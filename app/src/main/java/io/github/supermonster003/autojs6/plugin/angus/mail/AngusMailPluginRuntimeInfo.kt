package io.github.supermonster003.autojs6.plugin.angus.mail

/**
 * Pure-data view of the metadata reported through `IPluginInfoProvider.getInfo()` (and, once the
 * host contract is staged, `IMailPlugin.getInfo()` / `getCapabilities()`, roadmap P2.5).
 *
 * Android-specific lookups (package version, localized strings, raw resources) happen in
 * [angusMailPluginRuntimeInfo]; this class keeps the mapping itself testable on the JVM.
 */
data class AngusMailPluginRuntimeInfo(
    val name: String,
    val description: String,
    val instruction: String?,
    val versionName: String,
    val versionCode: Long,
    val versionDate: String,
) {
    val author: String get() = AngusMailPlugin.AUTHOR
    val id: String get() = AngusMailPlugin.ID
    val engine: String get() = AngusMailPlugin.ENGINE
    val variant: String get() = AngusMailPlugin.VARIANT

    /** Empty on purpose: the plugin ships no native code and runs on any ABI. */
    val supportedAbis: Array<String> get() = emptyArray()

    val requiresHostVersion: Long get() = AngusMailPlugin.REQUIRED_HOST_VERSION
}
