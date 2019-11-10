
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

        maven { url = uri("http://dl.bintray.com/lukaville/maven") }
        maven { url = uri("https://dl.bintray.com/icerockdev/moko") }
        maven { url = uri("https://dl.bintray.com/icerockdev/plugins") }
    }

    // workaround for https://youtrack.jetbrains.com/issue/KT-27170
    configurations.create("compileClasspath")
}

tasks.register("clean", Delete::class).configure {
    delete(rootProject.buildDir)
}
