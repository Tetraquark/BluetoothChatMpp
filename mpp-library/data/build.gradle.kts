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
    mppLibrary(MultiPlatformLibrary(
        android = Deps.kotlinStdlibAndroid,
        common = Deps.kotlinStdlibCommon
    ))

    mppLibrary(MultiPlatformLibrary(
        android = Deps.coroutinesAndroid,
        common = Deps.coroutinesCommon,
        iosArm64 = Deps.coroutinesNative,
        iosX64 = Deps.coroutinesNative
    ))

    mppModule(MultiPlatformModule(
        name = ":common:bluetooth",
        exported = true
    ))
}
