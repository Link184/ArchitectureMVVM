import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

object Versions {
    const val sqlDelight = "1.4.3"
}
plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("kotlin-android-extensions")
}
group = project.ext["GROUP"]!!
version = project.ext["VERSION_NAME"]!!

repositories {
    gradlePluginPortal()
    google()
    jcenter()
    mavenCentral()
}
kotlin {
    targets.all {
        compilations.all {
            kotlinOptions {
                freeCompilerArgs = freeCompilerArgs + "-Xallow-result-return-type" + "-Xopt-in=kotlin.RequiresOptIn"
            }
        }
    }

    android()
    jvm()
    iosX64("ios") {
        binaries {
            framework {
                baseName = "core"
            }
        }
    }
    js("nodeJs") {
        nodejs {
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$KOTLIN_COROUTINES_VERSION")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$KOTLIN_COROUTINES_VERSION")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$KOTLIN_COROUTINES_VERSION")
                implementation("androidx.core:core-ktx:${Android.ANDROIDX_CORE}")
                api("androidx.lifecycle:lifecycle-extensions:2.2.0")
                api("androidx.lifecycle:lifecycle-livedata-ktx:${Android.ANDROID_LIFECYCLE_VERSION}")
                api("androidx.lifecycle:lifecycle-viewmodel-ktx:${Android.ANDROID_LIFECYCLE_VERSION}")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
        val iosMain by getting {
            dependencies {
            }
        }
        val iosTest by getting
        val nodeJsMain by getting {
            dependencies {
            }
        }
        val nodeJsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}
android {
    compileSdkVersion(30)
    defaultConfig {
        minSdkVersion(24)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
        }
    }

    packagingOptions {
        exclude("DebugProbesKt.bin")
    }
}
val packForXcode by tasks.creating(Sync::class) {
    group = "build"
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val framework = kotlin.targets.getByName<KotlinNativeTarget>("ios").binaries.getFramework(mode)
    inputs.property("mode", mode)
    dependsOn(framework.linkTask)
    val targetDir = File(buildDir, "xcode-frameworks")
    from({ framework.outputDirectory })
    into(targetDir)
}
tasks.getByName("build").dependsOn(packForXcode)
