plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.multiplatform")
    id("dev.icerock.mobile.multiplatform")
}

group = "ru.tetraquark.mpp"
version = "1.0"

android {
    compileSdkVersion(Versions.Android.compileSdkVersion)

    defaultConfig {
        minSdkVersion(Versions.Android.minSdkVersion)
        targetSdkVersion(Versions.Android.targetSdkVersion)
    }
}

dependencies {
    mppLibrary(Deps.MultiPlatform.kotlinStdLib)

    androidLibrary(AndroidLibrary(
        name = Deps.Android.lifecycle
    ))
}
