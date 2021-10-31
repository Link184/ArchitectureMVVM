plugins {
    id("com.android.library")
    kotlin("android")
    id("com.vanniktech.maven.publish")
}

group = project.ext["GROUP"]!!
version = project.ext["VERSION_NAME"]!!

android {
    compileSdk = 31

    defaultConfig {
        minSdk = AndroidConfig.MIN_SDK_VERSION
        targetSdk = AndroidConfig.TARGET_SDK_VERSION
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

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
        buildConfig = false
        resValues = false
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Android.ANDROID_COMPOSE
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$KOTLIN_VERSION")

    implementation("androidx.compose.runtime:runtime:${Android.ANDROID_COMPOSE}")
    implementation("androidx.compose.runtime:runtime-livedata:${Android.ANDROID_COMPOSE}")
    implementation("androidx.compose.foundation:foundation:${Android.ANDROID_COMPOSE}")

    implementation(project(":common-mvvm"))

    testImplementation("junit:junit:4.13.2")
}
