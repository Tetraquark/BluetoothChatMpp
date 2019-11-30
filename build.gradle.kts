
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
