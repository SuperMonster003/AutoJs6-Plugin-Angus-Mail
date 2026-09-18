package io.github.supermonster003.autojs6.plugin.angus.mail.core.account

/** Transport security of one endpoint (roadmap appendix B.1 `tls`). */
enum class TlsMode(val id: String) {
    /** Implicit TLS from the first byte (IMAPS 993, POP3S 995, SMTPS 465). */
    SSL("ssl"),

    /** Plain connection upgraded with STARTTLS; the upgrade is mandatory, never opportunistic. */
    STARTTLS("starttls"),

    /** No encryption. Only for local test servers; the session is marked insecure (roadmap D25). */
    NONE("none"),
}

/** How the secret in [MailSecret] is presented to the server. */
enum class AuthMethod(val id: String) {
    /** Password, provider authorization code, or app password. */
    PASSWORD("password"),

    /** OAuth 2.0 access token sent through the XOAUTH2 SASL mechanism built into Angus Mail. */
    XOAUTH2("xoauth2"),
}

enum class MailProtocol(val id: String) {
    IMAP("imap"),
    POP3("pop3"),
    SMTP("smtp"),
}

data class MailEndpoint(
    val host: String,
    val port: Int,
    val tls: TlsMode = TlsMode.SSL,
) {
    init {
        require(host.isNotBlank()) { "host must not be blank" }
        require(port in 1..65535) { "port must be within 1..65535: $port" }
    }
}

/**
 * Connection facts of one account. Secrets live in [MailSecret], never here, so instances may be
 * logged and compared freely.
 */
data class MailAccount(
    val address: String,
    val username: String = address,
    val auth: AuthMethod = AuthMethod.PASSWORD,
    val imap: MailEndpoint? = null,
    val pop3: MailEndpoint? = null,
    val smtp: MailEndpoint? = null,
    /** Accept any server certificate and skip host name verification; marks the session insecure (roadmap D25). */
    val trustAll: Boolean = false,
    /** Connect, read, and write timeout of every socket. */
    val timeoutMillis: Long = DEFAULT_TIMEOUT_MILLIS,
) {
    init {
        require(address.isNotBlank()) { "address must not be blank" }
        require(username.isNotBlank()) { "username must not be blank" }
        require(timeoutMillis > 0) { "timeoutMillis must be positive" }
    }

    fun endpoint(protocol: MailProtocol): MailEndpoint = when (protocol) {
        MailProtocol.IMAP -> imap
        MailProtocol.POP3 -> pop3
        MailProtocol.SMTP -> smtp
    } ?: throw IllegalArgumentException("account $address has no ${protocol.id} endpoint")

    /** True when any configured endpoint runs without TLS or with certificate checks disabled. */
    val insecure: Boolean
        get() = trustAll || listOfNotNull(imap, pop3, smtp).any { it.tls == TlsMode.NONE }

    companion object {
        const val DEFAULT_TIMEOUT_MILLIS = 30_000L
    }
}

/**
 * A password, authorization code, or access token. The characters are wiped by [clear] and never
 * appear in [toString], so the object can travel through log statements without leaking.
 */
class MailSecret(secret: CharArray) {
    private val value: CharArray = secret.copyOf()

    constructor(secret: String) : this(secret.toCharArray())

    val isEmpty: Boolean get() = value.isEmpty()

    /** Materializes the secret for the Jakarta Mail authenticator, which only accepts strings. */
    fun reveal(): String = String(value)

    fun clear() {
        value.fill(' ')
    }

    override fun toString(): String = "MailSecret(***)"
}
