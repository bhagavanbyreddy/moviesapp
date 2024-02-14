plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "org.themoviedb.example"
    compileSdk = 34

    defaultConfig {
        applicationId = "org.themoviedb.example"
        minSdk = 24
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
            buildConfigField("String","BASE_URL","\"https://api.themoviedb.org/3/\"")

        }
        debug {

            buildConfigField("String","BASE_URL","\"https://api.themoviedb.org/3/\"")

        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

object Versions {
    const val retrofit_version = "2.9.0"
    const val okhttp_version = "4.10.0"
    const val room_version = "2.6.1"
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.1")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    //hilt
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    //hilt
    implementation("com.google.dagger:hilt-android:2.48")
    testImplementation("junit:junit:4.12")
    kapt("com.google.dagger:hilt-android-compiler:2.48")

    //retrofit
    implementation("com.squareup.retrofit2:retrofit:${Versions.retrofit_version}") {
        exclude(module = "okhttp")
    }
    implementation("com.squareup.retrofit2:converter-gson:${Versions.retrofit_version}")
    implementation("com.squareup.okhttp3:okhttp:${Versions.okhttp_version}")
    implementation("com.squareup.okhttp3:logging-interceptor:${Versions.okhttp_version}")

    //coil
    implementation("io.coil-kt:coil-compose:2.3.0")
    implementation("io.coil-kt:coil-svg:2.1.0")

    //navigation
    implementation ("androidx.navigation:navigation-compose:2.7.5")

    //Room
    implementation ("androidx.room:room-runtime:${Versions.room_version}")
    kapt ("androidx.room:room-compiler:${Versions.room_version}")
    implementation ("androidx.room:room-ktx:${Versions.room_version}")

    //unit test
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}

//// Allow references to generated code
kapt {
    correctErrorTypes = true
}