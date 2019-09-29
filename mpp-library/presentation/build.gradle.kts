plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.multiplatform")
    id("dev.icerock.mobile.multiplatform")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
}

androidExtensions {
    isExperimental = true
}


android {
    compileSdkVersion(Versions.Android.compileSdkVersion)

    defaultConfig {
        minSdkVersion(Versions.Android.minSdkVersion)
        targetSdkVersion(Versions.Android.targetSdkVersion)
    }

    dataBinding {
        isEnabled = true
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
        common = Deps.mokoMvvm,
        iosArm64 = Deps.mokoMvvmIosArm64,
        iosX64 = Deps.mokoMvvmIosX64
    ))

    androidLibrary(AndroidLibrary(
        name = Deps.Android.appcompat
    ))
    androidLibrary(AndroidLibrary(
        name = Deps.Android.androidxCoreKtx
    ))
    androidLibrary(AndroidLibrary(
        name = Deps.Android.constraintLayout
    ))
    androidLibrary(AndroidLibrary(
        name = Deps.Android.recyclerView
    ))
}
