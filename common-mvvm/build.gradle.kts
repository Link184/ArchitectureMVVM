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
                baseName = "common-mvvm"
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
                api("io.insert-koin:koin-core:$DI_KOIN_VERSION")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation("io.insert-koin:koin-test:$DI_KOIN_VERSION")
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
                api("androidx.appcompat:appcompat:1.3.1")
                api("androidx.core:core-ktx:${Android.ANDROIDX_CORE}")
                api("androidx.swiperefreshlayout:swiperefreshlayout:1.2.0-alpha01")

                api("io.insert-koin:koin-android:$DI_KOIN_VERSION")
                api("io.insert-koin:koin-android-ext:3.0.2")
                api("io.insert-koin:koin-androidx-compose:$DI_KOIN_VERSION")

                api("com.google.android.material:material:1.5.0-beta01")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))

                implementation("org.robolectric:robolectric:4.3.1")
                implementation("androidx.test:core-ktx:1.4.0")
//    testImplementation 'android.arch.core:core-testing:1.1.1'
                implementation("org.powermock:powermock-core:2.0.4")
                implementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.0")
                implementation("org.jetbrains.kotlin:kotlin-test:$KOTLIN_VERSION")
            }
        }
        val androidAndroidTest by getting {
            dependencies {
                implementation("androidx.test:runner:1.4.0")
                implementation("androidx.test.espresso:espresso-core:3.4.0")
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
    compileSdk = 31
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = AndroidConfig.MIN_SDK_VERSION
        targetSdk = AndroidConfig.TARGET_SDK_VERSION
    }

    packagingOptions {
        exclude("DebugProbesKt.bin")
    }
}
val packForXcode by tasks.creating(Sync::class) {
    group = "build"
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val framework = kotlin.targets.getByName<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget>("ios").binaries.getFramework(mode)
    inputs.property("mode", mode)
    dependsOn(framework.linkTask)
    val targetDir = File(buildDir, "xcode-frameworks")
    from({ framework.outputDirectory })
    into(targetDir)
}
tasks.getByName("build").dependsOn(packForXcode)
