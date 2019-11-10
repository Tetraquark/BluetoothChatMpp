object Deps {

    const val kotlinStdlibAndroid = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlinVersion}"
    const val kotlinStdlibCommon = "org.jetbrains.kotlin:kotlin-stdlib-common:${Versions.kotlinVersion}"

    object Android {
        const val appcompat = "androidx.appcompat:appcompat:${Versions.Android.appCompat}"
        const val androidxCoreKtx = "androidx.core:core-ktx:${Versions.Android.appCompat}"
        const val lifecycle = "androidx.lifecycle:lifecycle-extensions:${Versions.Android.lifecycle}"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.Android.constraintLayout}"
        const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.Android.recyclerView}"
    }

    const val coroutinesCommon = "org.jetbrains.kotlinx:kotlinx-coroutines-core-common:${Versions.coroutines}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val coroutinesNative = "org.jetbrains.kotlinx:kotlinx-coroutines-core-native:${Versions.coroutines}"

    const val kodeinCore = "org.kodein.di:kodein-di-core:${Versions.kodein}"
    const val kodeinErased = "org.kodein.di:kodein-di-erased:${Versions.kodein}"

    const val mokoCore = "dev.icerock.moko:core:${Versions.mokoCore}"
    const val mokoCoreIosX64 = "dev.icerock.moko:core-iosx64:${Versions.mokoCore}"
    const val mokoCoreIosArm64 = "dev.icerock.moko:core-iosarm64:${Versions.mokoCore}"

    const val mokoMvvm = "dev.icerock.moko:mvvm:${Versions.mokoMvvm}"
    const val mokoMvvmIosX64 = "dev.icerock.moko:mvvm-iosx64:${Versions.mokoMvvm}"
    const val mokoMvvmIosArm64 = "dev.icerock.moko:mvvm-iosarm64:${Versions.mokoMvvm}"

    const val mokoWidgets = "dev.icerock.moko:widgets:${Versions.mokoWidgets}"
    const val mokoWidgetsIosX64 = "dev.icerock.moko:widgets-iosx64:${Versions.mokoWidgets}"
    const val mokoWidgetsIosArm64 = "dev.icerock.moko:widgets-iosarm64:${Versions.mokoWidgets}"

    const val mokoUnits = "dev.icerock.moko:units:${Versions.mokoUnits}"
    const val mokoUnitsIosX64 = "dev.icerock.moko:units-iosx64:${Versions.mokoUnits}"
    const val mokoUnitsIosArm64 = "dev.icerock.moko:units-iosarm64:${Versions.mokoUnits}"
}
