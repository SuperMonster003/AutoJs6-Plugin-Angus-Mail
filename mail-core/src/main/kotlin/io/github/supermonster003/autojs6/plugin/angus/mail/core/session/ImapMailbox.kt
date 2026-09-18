package io.github.supermonster003.autojs6.plugin.angus.mail.core.session

import io.github.supermonster003.autojs6.plugin.angus.mail.core.account.MailAccount
import io.github.supermonster003.autojs6.plugin.angus.mail.core.account.MailProtocol
import io.github.supermonster003.autojs6.plugin.angus.mail.core.account.MailSecret
import io.github.supermonster003.autojs6.plugin.angus.mail.core.message.MessageSummary
import jakarta.mail.FetchProfile
import jakarta.mail.Folder
import jakarta.mail.UIDFolder
import org.eclipse.angus.mail.imap.IMAPFolder
import org.eclipse.angus.mail.imap.IMAPStore
import java.io.Closeable

/** One connected IMAP store. Minimal P0.2 surface: list the inbox, report capabilities, open folders. */
class ImapMailbox private constructor(private val store: IMAPStore) : Closeable {

    val isConnected: Boolean get() = store.isConnected

    fun hasCapability(name: String): Boolean = store.hasCapability(name)

    /** Newest [limit] messages of INBOX, newest first, without marking them as read. */
    fun listInbox(limit: Int): List<MessageSummary> = withFolder(INBOX, Folder.READ_ONLY) { folder ->
        val total = folder.messageCount
        if (total == 0 || limit <= 0) return@withFolder emptyList()
        val messages = folder.getMessages(maxOf(1, total - limit + 1), total)
        folder.fetch(messages, FetchProfile().apply {
            add(FetchProfile.Item.ENVELOPE)
            add(FetchProfile.Item.FLAGS)
            add(FetchProfile.Item.SIZE)
            add(UIDFolder.FetchProfileItem.UID)
        })
        messages.map { MessageSummary.of(it) }.asReversed()
    }

    fun <T> withFolder(name: String, mode: Int, block: (IMAPFolder) -> T): T {
        val folder = store.getFolder(name) as IMAPFolder
        folder.open(mode)
        try {
            return block(folder)
        } finally {
            if (folder.isOpen) folder.close(false)
        }
    }

    override fun close() {
        if (store.isConnected) store.close()
    }

    companion object {
        const val INBOX = "INBOX"

        fun connect(account: MailAccount, secret: MailSecret): ImapMailbox =
            ImapMailbox(MailSessionFactory.connectStore(account, MailProtocol.IMAP, secret) as IMAPStore)
    }
}
