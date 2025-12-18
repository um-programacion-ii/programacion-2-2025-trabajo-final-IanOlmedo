pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
    plugins {
        id("com.android.application") version "8.4.1"
        id("com.android.library") version "8.4.1"

        kotlin("android") version "2.0.0"
        kotlin("multiplatform") version "2.0.0"
        kotlin("plugin.serialization") version "2.0.0"
        id("org.jetbrains.compose") version "1.6.10"
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "gestioneventos-mobile"
include(":androidApp")
include(":shared")
