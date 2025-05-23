import org.gradle.api.tasks.Delete

plugins {
    id("com.android.application") version "8.9.1" apply false
    id("com.android.library") version "8.9.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
