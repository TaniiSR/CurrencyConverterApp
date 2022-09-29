object BuildClassesConfig {
    const val ANDROID_GRADLE_PLUGIN =
        "com.android.tools.build:gradle:${BuildPluginsVersions.BUILDGRADLE}"
    const val KOTLIN_GRADLE_PLUGIN =
        "org.jetbrains.kotlin:kotlin-gradle-plugin:${BuildPluginsVersions.KOTLIN}"
    const val HILT_GRADLE_PLUGIN =
        "com.google.dagger:hilt-android-gradle-plugin:${BuildPluginsVersions.HILT}"
    const val NAVIGATION_SAFE_ARGS =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${BuildPluginsVersions.NAVIGATION}"
}