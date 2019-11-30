object Modules {
    object MultiPlatform {

        object Common {
            val bluetooth = MultiPlatformModule(
                name = ":common:bluetooth",
                exported = true
            )
        }

        val domain = MultiPlatformModule(
            name = ":mpp-library:domain",
            exported = true
        )

        val presentation = MultiPlatformModule(
            name = ":mpp-library:presentation",
            exported = true
        )

        val data = MultiPlatformModule(
            name = ":mpp-library:data",
            exported = true
        )
    }
}
