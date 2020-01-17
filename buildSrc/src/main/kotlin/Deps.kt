object Deps {

    const val kotlinStdlibAndroid = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlinVersion}"
    const val kotlinStdlibCommon = "org.jetbrains.kotlin:kotlin-stdlib-common:${Versions.kotlinVersion}"

    object Plugins {
        const val androidExtensions =
            "org.jetbrains.kotlin:kotlin-android-extensions:${Versions.Plugins.androidExtensions}"
    }

    val plugins: Map<String, String> = mapOf(
        "kotlin-android-extensions" to Plugins.androidExtensions
    )

    object Android {
        const val appcompat = "androidx.appcompat:appcompat:${Versions.Android.appCompat}"
        const val androidxCoreKtx = "androidx.core:core-ktx:${Versions.Android.appCompat}"
        const val lifecycle = "androidx.lifecycle:lifecycle-extensions:${Versions.Android.lifecycle}"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.Android.constraintLayout}"
        const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.Android.recyclerView}"
    }

    object MultiPlatform {

        val kotlinStdLib = MultiPlatformLibrary(
            android = Deps.kotlinStdlibAndroid,
            common = Deps.kotlinStdlibCommon
        )

        val coroutines = MultiPlatformLibrary(
            android = Deps.coroutinesAndroid,
            common = Deps.coroutinesCommon,
            ios = Deps.coroutinesNative
        )

        val napier = MultiPlatformLibrary(
            common = Deps.napierCommon,
            iosArm64 = Deps.napieriosArm64,
            iosX64 = Deps.napieriosX64,
            android = Deps.napierAndroid
        )

        val mokoMvvm = MultiPlatformLibrary(
            common = Deps.mokoMvvm,
            iosArm64 = Deps.mokoMvvmIosArm64,
            iosX64 = Deps.mokoMvvmIosX64
        )

        val mokoPermissions = MultiPlatformLibrary(
            common = Deps.mokoPermissions,
            iosArm64 = Deps.mokoPermissionsIosArm64,
            iosX64 = Deps.mokoPermissionsIosX64
        )
    }

    const val coroutinesCommon = "org.jetbrains.kotlinx:kotlinx-coroutines-core-common:${Versions.coroutines}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val coroutinesNative = "org.jetbrains.kotlinx:kotlinx-coroutines-core-native:${Versions.coroutines}"

    //const val kodeinCore = "org.kodein.di:kodein-di-core:${Versions.kodein}"
    //const val kodeinErased = "org.kodein.di:kodein-di-erased:${Versions.kodein}"

    const val napierCommon = "com.github.aakira:napier:${Versions.napier}"
    const val napierAndroid = "com.github.aakira:napier-android:${Versions.napier}"
    const val napieriosArm64 = "com.github.aakira:napier-iosArm64:${Versions.napier}"
    const val napieriosX64 = "com.github.aakira:napier-iosX64:${Versions.napier}"

    const val mokoMvvm = "dev.icerock.moko:mvvm:${Versions.mokoMvvm}"
    const val mokoMvvmIosX64 = "dev.icerock.moko:mvvm-iosx64:${Versions.mokoMvvm}"
    const val mokoMvvmIosArm64 = "dev.icerock.moko:mvvm-iosarm64:${Versions.mokoMvvm}"

    const val mokoPermissions = "dev.icerock.moko:permissions:${Versions.mokoPermissions}"
    const val mokoPermissionsIosX64 = "dev.icerock.moko:permissions-iosx64:${Versions.mokoPermissions}"
    const val mokoPermissionsIosArm64 = "dev.icerock.moko:permissions-iosarm64:${Versions.mokoPermissions}"

}
