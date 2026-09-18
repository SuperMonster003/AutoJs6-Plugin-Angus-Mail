# Applied to the instrumentation APK only, and only when -PandroidTestRelease switches testBuildType
# to release (AGP then shrinks the test APK against the release mapping). androidx.test references
# Error Prone annotations that are never packaged.
-dontwarn com.google.errorprone.annotations.**
