package io.github.supermonster003.autojs6.plugin.angus.mail.core.session

import io.github.supermonster003.autojs6.plugin.angus.mail.core.account.MailAccount
import io.github.supermonster003.autojs6.plugin.angus.mail.core.account.MailProtocol
import io.github.supermonster003.autojs6.plugin.angus.mail.core.account.MailSecret
import io.github.supermonster003.autojs6.plugin.angus.mail.core.message.MessageSummary
import jakarta.mail.FetchProfile
import jakarta.mail.Folder
import jakarta.mail.Store
import java.io.Closeable

/** One connected POP3 store. POP3 exposes a single read-only INBOX; roadmap P2 adds the degraded operation set. */
class Pop3Mailbox private constructor(private val store: Store) : Closeable {

    val isConnected: Boolean get() = store.isConnected

    /** Newest [limit] messages, newest first. POP3 has no IMAP-style UIDs, so `uid` is null. */
    fun listInbox(limit: Int): List<MessageSummary> {
        val folder = store.getFolder(ImapMailbox.INBOX)
        folder.open(Folder.READ_ONLY)
        try {
            val total = folder.messageCount
            if (total == 0 || limit <= 0) return emptyList()
            val messages = folder.getMessages(maxOf(1, total - limit + 1), total)
            folder.fetch(messages, FetchProfile().apply {
                add(FetchProfile.Item.ENVELOPE)
                add(FetchProfile.Item.SIZE)
            })
            return messages.map { MessageSummary.of(it) }.asReversed()
        } finally {
            if (folder.isOpen) folder.close(false)
        }
    }

    override fun close() {
        if (store.isConnected) store.close()
    }

    companion object {
        fun connect(account: MailAccount, secret: MailSecret): Pop3Mailbox =
            Pop3Mailbox(MailSessionFactory.connectStore(account, MailProtocol.POP3, secret))
    }
}
