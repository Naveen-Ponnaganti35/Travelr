plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("kapt")
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "uk.ac.tees.mad.travelr"
    compileSdk = 36

    defaultConfig {
        applicationId = "uk.ac.tees.mad.travelr"
        minSdk = 26
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.firebase.database)
    implementation(libs.firebase.firestore)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)



    implementation("androidx.room:room-runtime:2.7.0-alpha05")
    kapt("androidx.room:room-compiler:2.7.0-alpha05")
    implementation("androidx.room:room-ktx:2.7.0-alpha05")
    implementation("io.coil-kt:coil-compose:2.7.0")

// Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")



    // Coil for image loading

//    implementation("io.coil-kt.coil3:coil-compose:3.3.0")


    // Retrofit and Json Convertion
    //    network calls
    implementation( "com.squareup.retrofit2:retrofit:2.11.0")


//    Json to kotlin object convertor mapping
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // OkHttp (optional but recommended)
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")


    implementation("androidx.compose.material:material-icons-extended:1.6.1")

    // notification
    implementation("com.google.accompanist:accompanist-permissions:0.31.1-alpha")


    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.work:work-runtime-ktx:2.9.1") // for background scheduling


    //maps
    implementation("org.maplibre.gl:android-sdk:11.13.5")

    implementation("com.google.android.gms:play-services-maps:18.1.0")


// Check latest on GitHub
// Optional: For better Compose integration
    implementation("androidx.compose.ui:ui:1.6.0")
// Your version

    implementation("org.osmdroid:osmdroid-android:6.1.18")


}
