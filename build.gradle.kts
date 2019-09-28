
buildscript {
    repositories {
        google()
        jcenter()
        maven { url = uri("https://dl.bintray.com/icerockdev/plugins") }
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.5.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}")
        classpath("dev.icerock:mobile-multiplatform:0.3.0")
    }
}


allprojects {
    repositories {
        google()
        jcenter()

        maven { url = uri("https://dl.bintray.com/icerockdev/moko") }
    }
}

tasks.register("clean", Delete::class).configure {
    delete(rootProject.buildDir)
}
