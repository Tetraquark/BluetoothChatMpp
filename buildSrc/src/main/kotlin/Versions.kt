object Versions {

    const val kotlinVersion = "1.3.61"

    object Plugins {
        const val androidExtensions = kotlinVersion
    }

    object Android {
        const val compileSdkVersion = 28
        const val minSdkVersion = 21
        const val targetSdkVersion = 28

        const val appCompat = "1.1.0"
        const val lifecycle = "2.1.0"
        const val constraintLayout = "1.1.3"
        const val recyclerView = "1.1.0-beta04"
    }

    const val coroutines = "1.3.3"

    const val kodein = "6.4.0" // not used
    
    const val mokoMvvm = "0.4.0"
    const val mokoPermissions = "0.4.0"

}
