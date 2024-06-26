plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.gms.google-services")
    id ("kotlin-parcelize")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.uit.moneykeeper"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.uit.moneykeeper"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.core.i18n)
    implementation(libs.places)
    implementation(libs.material)
    implementation(libs.androidx.compose.material)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.firestore.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))
    implementation ("co.yml:ycharts:2.1.0")
    implementation("androidx.compose.material3:material3:1.2.1")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.android.gms:play-services-auth:21.1.1")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.credentials:credentials:1.3.0-alpha03")
    implementation("androidx.credentials:credentials-play-services-auth:1.3.0-alpha03")
    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.0")
    implementation ("androidx.compose.material3:material3:1.2.0-alpha05")
    implementation ("com.maxkeppeler.sheets-compose-dialogs:calendar:1.0.2")
    implementation ("com.maxkeppeler.sheets-compose-dialogs:core:1.0.2")
    implementation("com.google.dagger:hilt-android:2.44.2")
    kapt("com.google.dagger:hilt-android-compiler:2.44.2")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation("io.coil-kt:coil-compose:2.2.2")
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
    implementation("androidx.compose.material:material-icons-extended:1.6.7")
    implementation("androidx.compose.runtime:runtime:1.6.7")
    implementation("androidx.compose.material:material:1.6.7")
    implementation ("com.maxkeppeler.sheets-compose-dialogs:calendar:1.0.2")
    implementation ("com.maxkeppeler.sheets-compose-dialogs:core:1.0.2")
    implementation ("com.maxkeppeler.sheets-compose-dialogs:core:1.3.0")
    implementation ("com.maxkeppeler.sheets-compose-dialogs:calendar:1.3.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.5.2")
}