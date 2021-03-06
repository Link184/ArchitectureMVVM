plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-android-extensions")
}
android {
    compileSdkVersion(30)

    defaultConfig {
        applicationId = "com.link184.sample"
        minSdkVersion(AndroidConfig.MIN_SDK_VERSION)
        targetSdkVersion(AndroidConfig.TARGET_SDK_VERSION)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner("android.support.test.runner.AndroidJUnitRunner")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled =  false
            setProguardFiles(listOf(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"))
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

configurations {
    all {
        resolutionStrategy.dependencySubstitution {
            substitute(module("io.reactivex.rxjava2:rxjava")).with(module("io.reactivex.rxjava3:rxjava:3.0.0-RC1"))
        }
    }
}


repositories {
    maven(url = "https://oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$KOTLIN_VERSION")
    implementation("com.android.support.constraint:constraint-layout:2.0.4")
    implementation(project(":common-mvvm"))
//    implementation "com.link184:common-mvvm:0.6.7-SNAPSHOT"

    testImplementation("junit:junit:4.13.1")
    androidTestImplementation("com.android.support.test:runner:1.0.2")
    androidTestImplementation("com.android.support.test.espresso:espresso-core:3.0.2")

    // RxJava3
    implementation("io.reactivex.rxjava3:rxjava:3.0.5")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")

    // Common extensions
    implementation("com.link184:common-extensions:1.0.3")

    implementation("com.link184:kid-adapter:1.3.8-SNAPSHOT")
}
