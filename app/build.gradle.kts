plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.firebase)
}

android {
    namespace = "com.example.teamez" // your actual namespace
    compileSdk = 35
    android {
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }

        kotlinOptions {
            jvmTarget = "1.8"  // make sure this matches
        }
    }


    defaultConfig {
        applicationId = "com.example.teamez"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
        viewBinding = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0" // Use latest stable Compose version
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
    implementation(libs.androidx.material3)
    implementation(libs.androidx.appcompat)
    implementation ("androidx.compose.material3:material3:1.2.1")
    implementation ("com.google.accompanist:accompanist-permissions:0.34.0")
    implementation ("com.google.android.gms:play-services-location:21.0.1")


    // ✅ Needed for calendar year selection picker
    implementation("com.google.android.material:material:1.12.0")
    implementation ("androidx.compose.material3:material3:1.1.2")





    // ✅ Fragment extensions
    implementation("androidx.fragment:fragment-ktx:1.8.8")

    // Firebase & location
    implementation(libs.firebase.auth)
    implementation(libs.playservices.location)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation ("androidx.compose.ui:ui:1.5.0")
    implementation ("androidx.compose.material3:material3:1.1.2")
    implementation ("androidx.activity:activity-compose:1.8.0")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.5.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    // Jetpack Compose BOM
    implementation ("androidx.compose:compose-bom:2024.05.00")
    implementation(platform("androidx.compose:compose-bom:2024.04.00"))
    implementation("androidx.compose.foundation:foundation-layout")


// Material 3
    implementation ("androidx.compose.material3:material3")

// Icons
    implementation ("androidx.compose.material:material-icons-extended")

// For FlowRow
    implementation ("com.google.accompanist:accompanist-permissions:0.28.0")

// For location
    implementation ("com.google.android.gms:play-services-location:21.0.1")
    implementation ("io.coil-kt:coil-compose:2.4.0")
    implementation ("androidx.compose.foundation:foundation:1.8.3")

}

apply(plugin = "com.google.gms.google-services")
