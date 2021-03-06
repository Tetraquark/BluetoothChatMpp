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
        common = Deps.mokoCore,
        iosArm64 = Deps.mokoCoreIosArm64,
        iosX64 = Deps.mokoCoreIosX64
    ))

    mppLibrary(MultiPlatformLibrary(
        android = Deps.coroutinesAndroid,
        common = Deps.coroutinesCommon,
        iosArm64 = Deps.coroutinesNative,
        iosX64 = Deps.coroutinesNative
    ))

    mppModule(MultiPlatformModule(
        name = ":mpp-library:data",
        exported = true
    ))

    mppModule(MultiPlatformModule(
        name = ":mpp-library:domain",
        exported = true
    ))

    mppModule(MultiPlatformModule(
        name = ":mpp-library:presentation",
        exported = true
    ))

    androidLibrary(AndroidLibrary(
        name = Deps.Android.lifecycle
    ))

    androidLibrary(AndroidLibrary(
        name = Deps.Android.appcompat
    ))
}
