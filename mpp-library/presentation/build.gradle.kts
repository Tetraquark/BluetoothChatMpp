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
    mppLibrary(Deps.MultiPlatform.kotlinStdLib)
    mppLibrary(Deps.MultiPlatform.coroutines)

    //mppLibrary(Deps.MultiPlatform.mokoCore)
    mppLibrary(Deps.MultiPlatform.mokoMvvm)
    mppLibrary(Deps.MultiPlatform.mokoPermissions)

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
