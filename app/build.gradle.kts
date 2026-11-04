plugins {
    alias(libs.plugins.android.application)
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "br.unisanta.tp07firebase"
    compileSdk = 36

    defaultConfig {
        applicationId = "br.unisanta.tp07firebase"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
        viewBinding = true
    }
}

configurations.all {
    resolutionStrategy {
        force("androidx.core:core-ktx:1.13.1")
        force("androidx.core:core:1.13.1")
        force("androidx.activity:activity-ktx:1.9.0")
        force("androidx.activity:activity:1.9.0")
        force("androidx.appcompat:appcompat:1.7.0")
        force("androidx.constraintlayout:constraintlayout:2.1.4")
        force("org.jetbrains.kotlin:kotlin-stdlib:1.9.0")
        force("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.9.0")
        force("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.0")
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation(libs.material)
    implementation("androidx.activity:activity-ktx:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    implementation(platform("com.google.firebase:firebase-bom:32.8.1"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore-ktx:24.11.1")
    implementation("com.firebaseui:firebase-ui-auth:8.0.2")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}