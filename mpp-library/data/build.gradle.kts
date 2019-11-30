plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.multiplatform")
    id("dev.icerock.mobile.multiplatform")
}

android {
    compileSdkVersion(Versions.Android.compileSdkVersion)

    defaultConfig {
        minSdkVersion(Versions.Android.minSdkVersion)
        targetSdkVersion(Versions.Android.targetSdkVersion)
    }
}

dependencies {
    mppLibrary(Deps.MultiPlatform.kotlinStdLib)

    mppLibrary(Deps.MultiPlatform.coroutines)

    mppModule(MultiPlatformModule(
        name = ":common:bluetooth",
        exported = true
    ))
}
