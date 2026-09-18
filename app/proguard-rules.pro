-dontwarn kotlinx.parcelize.Parcelize

-keep class io.github.supermonster003.autojs6.plugin.angus.mail.AngusMailPluginInfoService { *; }
-keep class io.github.supermonster003.autojs6.plugin.angus.mail.AngusMailPluginService { *; }
-keep class io.github.supermonster003.autojs6.plugin.angus.mail.WakeActivity { *; }

-keep class org.autojs.plugin.common.api.** { *; }

# Jakarta Mail / Angus Mail (roadmap P0.2): providers, transports, stores, and the MIME data handlers
# are instantiated by name (META-INF/javamail.default.providers, META-INF/mailcap, ServiceLoader),
# so the shrinker must keep every class of both namespaces with its original name. P6 revisits the
# scope of this rule once the provider set of the release build is measured.
-keep class jakarta.mail.** { *; }
-keep class jakarta.activation.** { *; }
-keep class org.eclipse.angus.mail.** { *; }
-keep class org.eclipse.angus.activation.** { *; }

# Optional integrations Angus Mail references but Android never provides. Verified by
# :app:assembleRelease on 2026-09-18 (R8 8.13.19): java.awt.* is reached only from the image_gif /
# image_jpeg handlers, javax.security.auth.callback.NameCallback only from the SASL client
# (OAuth2SaslClient), which Android cannot use anyway; the built-in XOAUTH2 path stays intact.
-dontwarn java.awt.**
-dontwarn java.beans.**
-dontwarn javax.naming.**
-dontwarn javax.security.auth.callback.**
-dontwarn javax.security.sasl.**
-dontwarn java.lang.management.**
-dontwarn javax.management.**
-dontwarn org.graalvm.**
-dontwarn jdk.**
