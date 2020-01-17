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

val mppLibs = listOf(
    Deps.MultiPlatform.kotlinStdLib,
    Deps.MultiPlatform.coroutines,
    //Deps.MultiPlatform.mokoCore,
    Deps.MultiPlatform.mokoMvvm,
    Deps.MultiPlatform.mokoPermissions
)

val mppModules = listOf(
    Modules.MultiPlatform.Common.bluetooth,
    Modules.MultiPlatform.data,
    Modules.MultiPlatform.domain,
    Modules.MultiPlatform.presentation
)

setupFramework(
    exports = mppLibs + mppModules
)

dependencies {

    androidLibrary(AndroidLibrary(
        name = Deps.Android.lifecycle
    ))

    androidLibrary(AndroidLibrary(
        name = Deps.Android.appcompat
    ))

    mppLibs.forEach { mppLibrary(it) }
    mppModules.forEach { mppModule(it) }
}

// dependencies graph generator
apply(from = "https://raw.githubusercontent.com/JakeWharton/SdkSearch/master/gradle/projectDependencyGraph.gradle")
