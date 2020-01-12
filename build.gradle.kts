
allprojects {
    repositories {
        google()
        jcenter()

        maven { url = uri("https://dl.bintray.com/icerockdev/moko") }
        maven { url = uri("https://dl.bintray.com/aakira/maven") }
    }
    configurations.create("compileClasspath")
}

tasks.register("clean", Delete::class).configure {
    delete(rootProject.buildDir)
}
