import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("kotlin-android-extensions")
    id("org.jetbrains.dokka")
    id("com.vanniktech.maven.publish")
}

group = project.ext["GROUP"]!!
version = project.ext["VERSION_NAME"]!!

val ktlint by configurations.creating

tasks.register<JavaExec>("ktlint") {
    group = "verification"
    description = "Check Kotlin code style."
    classpath = ktlint
    main = "com.github.shyiko.ktlint.Main"
    args("--android", "src/**/*.kt")
}

tasks.named("check") {
    dependsOn(ktlint)
}

tasks.register<JavaExec>("ktlintFormat") {
    group = "formatting"
    description = "Fix Kotlin code style deviations."
    classpath = ktlint
    main = "com.github.shyiko.ktlint.Main"
    args("--android", "-F", "src/**/*.kt")
}

dependencies {
    ktlint("com.github.shyiko:ktlint:0.29.0")
}

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

    android() {
        publishLibraryVariants("release")
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
    }
    jvm()

    iosX64("ios") {
        binaries {
            framework {
                baseName = "common-main"
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

                api("org.jetbrains.kotlin:kotlin-stdlib:$KOTLIN_VERSION")
                api("androidx.appcompat:appcompat:1.2.0")
                api("androidx.core:core-ktx:${Android.ANDROIDX_CORE}")
                api("androidx.swiperefreshlayout:swiperefreshlayout:1.2.0-alpha01")

                api("com.google.android.material:material:1.1.0-alpha10")

                api("org.koin:koin-androidx-viewmodel:2.2.0-rc-2")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))

                implementation("org.robolectric:robolectric:4.3.1")
                implementation("androidx.test:core-ktx:1.3.0")
//    testImplementation 'android.arch.core:core-testing:1.1.1'
                implementation("org.powermock:powermock-core:2.0.4")
                implementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
                implementation("org.koin:koin-test:2.0.1")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.8")
                implementation("org.jetbrains.kotlin:kotlin-test:$KOTLIN_VERSION")
            }
        }
        val androidAndroidTest by getting {
            dependencies {
                implementation("androidx.test:runner:1.3.0")
                implementation("androidx.test.espresso:espresso-core:3.3.0")
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
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(AndroidConfig.MIN_SDK_VERSION)
        targetSdkVersion(AndroidConfig.TARGET_SDK_VERSION)
        versionCode = 1
        versionName = "1.0"
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
