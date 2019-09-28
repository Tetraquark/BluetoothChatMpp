enableFeaturePreview("GRADLE_METADATA")

include(":android-app")
include(":mpp-library")
include(":mpp-library:data")
include(":mpp-library:domain")
include(":mpp-library:presentation")
include(":common:bluetooth")

rootProject.name = "BluetoothChatMpp"
