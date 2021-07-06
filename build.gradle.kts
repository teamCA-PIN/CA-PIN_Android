// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val kotlin_version by extra("1.5.20")
    repositories {
        google()
        mavenCentral()

    }
    dependencies {
        classpath("com.android.tools.build:gradle:${BuildDependencyVersions.GRADLE}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${BuildDependencyVersions.KOTLIN}")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${BuildDependencyVersions.NAVIGATION}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${BuildDependencyVersions.HILT}")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven {
            setUrl("https://naver.jfrog.io/artifactory/maven/")
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}