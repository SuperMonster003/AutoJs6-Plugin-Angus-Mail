package io.github.supermonster003.autojs6.plugin.angus.mail.core.session

import io.github.supermonster003.autojs6.plugin.angus.mail.core.account.MailAccount
import io.github.supermonster003.autojs6.plugin.angus.mail.core.account.MailProtocol
import io.github.supermonster003.autojs6.plugin.angus.mail.core.account.MailSecret
import jakarta.activation.DataHandler
import jakarta.activation.FileDataSource
import jakarta.mail.Message
import jakarta.mail.Session
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeBodyPart
import jakarta.mail.internet.MimeMessage
import jakarta.mail.internet.MimeMultipart
import java.io.File
import java.util.Date

/** Minimal outgoing message; roadmap P2 extends it with cc / bcc / inline images / headers / priority. */
data class OutgoingMessage(
    val to: List<String>,
    val subject: String,
    val text: String,
    val html: String? = null,
    val attachments: List<File> = emptyList(),
    val from: String? = null,
) {
    init {
        require(to.isNotEmpty()) { "at least one recipient is required" }
    }
}

/** Sends messages through the account's SMTP endpoint. Each call opens and closes its own transport. */
class SmtpSender(private val account: MailAccount, private val secret: MailSecret) {

    /** Builds the MIME message without sending it, so tests can parse the exact bytes that would go out. */
    fun compose(message: OutgoingMessage): MimeMessage =
        compose(MailSessionFactory.session(account, MailProtocol.SMTP, secret), message)

    /** Sends [message] and returns its `Message-ID`. */
    fun send(message: OutgoingMessage): String {
        val mime = compose(message)
        val transport = MailSessionFactory.connectTransport(account, secret)
        try {
            transport.sendMessage(mime, mime.allRecipients)
        } finally {
            transport.close()
        }
        return mime.messageID
    }

    private fun compose(session: Session, message: OutgoingMessage): MimeMessage {
        val mime = MimeMessage(session)
        mime.setFrom(InternetAddress(message.from ?: account.address))
        mime.setRecipients(Message.RecipientType.TO, message.to.map { InternetAddress(it) }.toTypedArray())
        mime.setSubject(message.subject, "UTF-8")
        mime.sentDate = Date()

        if (message.attachments.isEmpty() && message.html == null) {
            mime.setText(message.text, "UTF-8")
        } else {
            val body = if (message.html == null) {
                MimeBodyPart().apply { setText(message.text, "UTF-8") }
            } else {
                MimeBodyPart().apply {
                    setContent(MimeMultipart("alternative").apply {
                        addBodyPart(MimeBodyPart().apply { setText(message.text, "UTF-8") })
                        addBodyPart(MimeBodyPart().apply { setContent(message.html, "text/html; charset=UTF-8") })
                    })
                }
            }
            mime.setContent(MimeMultipart("mixed").apply {
                addBodyPart(body)
                message.attachments.forEach { file ->
                    addBodyPart(MimeBodyPart().apply {
                        dataHandler = DataHandler(FileDataSource(file))
                        fileName = file.name
                        disposition = MimeBodyPart.ATTACHMENT
                        // Byte-exact attachments: a text part sent as 7bit loses the line break that
                        // precedes the closing MIME boundary and is subject to line-ending rewrites.
                        setHeader("Content-Transfer-Encoding", "base64")
                    })
                }
            })
        }
        mime.saveChanges()
        return mime
    }
}
