# Added to the application rules only for -PandroidTestRelease builds (roadmap P0.2 / AGENTS.md 5.3):
# the instrumentation tests call into the plugin and mail core surface by name, so a release build
# under test must keep those members that the shipped build is free to strip. The test APK is
# shrunk against the application mapping and does not bundle its own Kotlin runtime, so the
# application must also keep the Kotlin standard library classes the test code links against.
# Never part of the regular release build.
-keep class io.github.supermonster003.autojs6.plugin.angus.mail.** { *; }
-keep class kotlin.** { *; }
-dontwarn kotlin.**
