plugins {
    `kotlin-dsl`
}

repositories {
    mavenLocal()
    maven { url = uri("https://dl.bintray.com/icerockdev/plugins") }

    jcenter()
    google()
}

dependencies {
    implementation("dev.icerock:mobile-multiplatform:0.3.0")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.50")
    implementation("com.android.tools.build:gradle:3.5.0")
}
