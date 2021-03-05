plugins {
    id("com.android.library")
    kotlin("android")
    id("kotlin-android-extensions")
    id("com.vanniktech.maven.publish")
}

android {
    compileSdkVersion(30)
    defaultConfig {
        minSdkVersion(19)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    val GROUP : String by project
    val POM_ARTIFACT_ID : String by project

    compileOptions {
        kotlinOptions.freeCompilerArgs = listOf("-module-name", "$GROUP.$POM_ARTIFACT_ID")
        kotlinOptions.useIR = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    testOptions.unitTests.isIncludeAndroidResources = true
}

//configurations {
//    ktlint
//}
//
//task ktlint(type: JavaExec, group: "verification") {
//    description = "Check Kotlin code style."
//    classpath = configurations.ktlint
//    main = "com.github.shyiko.ktlint.Main"
//    args "src/**/*.kt"
//    // to generate report in checkstyle format prepend following args:
//    // "--reporter=plain", "--reporter=checkstyle,output=${buildDir}/ktlint.xml"
//    // see https://github.com/shyiko/ktlint#usage for more
//}
//check.dependsOn ktlint
//
//task ktlintFormat(type: JavaExec, group: "formatting") {
//    description = "Fix Kotlin code style deviations."
//    classpath = configurations.ktlint
//    main = "com.github.shyiko.ktlint.Main"
//    args "-F", "src/**/*.kt"
//}

dependencies {
//    ktlint "com.github.shyiko:ktlint:0.29.0"

    api("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$KOTLIN_VERSION")
    api("androidx.appcompat:appcompat:1.2.0")
    api("androidx.core:core-ktx:1.3.2")
    api("androidx.lifecycle:lifecycle-extensions:2.2.0")
    api("androidx.lifecycle:lifecycle-livedata-ktx:2.3.0")
    api("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.0")
    api("androidx.swiperefreshlayout:swiperefreshlayout:1.2.0-alpha01")

    api("com.google.android.material:material:1.1.0-alpha10")

    api("org.koin:koin-androidx-viewmodel:2.2.0-rc-2")

    testImplementation("junit:junit:4.13.1")
    androidTestImplementation("androidx.test:runner:1.3.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")

    testImplementation("org.robolectric:robolectric:4.3.1")
    testImplementation("androidx.test:core-ktx:1.3.0")
//    testImplementation 'android.arch.core:core-testing:1.1.1'
    testImplementation("org.powermock:powermock-core:2.0.4")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
    testImplementation("org.koin:koin-test:2.0.1")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.8")
    testImplementation("org.jetbrains.kotlin:kotlin-test:$KOTLIN_VERSION")
}
