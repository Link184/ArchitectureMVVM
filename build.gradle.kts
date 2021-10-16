// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.2.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$KOTLIN_VERSION")

        classpath("com.vanniktech:gradle-maven-publish-plugin:0.18.0")
        classpath("org.jetbrains.dokka:dokka-gradle-plugin:$KOTLIN_VERSION")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}
