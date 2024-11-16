plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.doxpass.suncity"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.doxpass.suncity"
        minSdk = 21
        targetSdk = 34
        versionCode = 2
        versionName = "1.1"

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

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation("com.google.firebase:firebase-database:20.3.1")
    testImplementation("junit:junit:4.13.2")

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation ("androidx.activity:activity-compose:1.3.0")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation( "androidx.compose.runtime:runtime-livedata:1.6.0")
    implementation("androidx.compose.ui:ui-graphics")
    implementation ("io.coil-kt:coil-compose:1.3.2")
    implementation ("androidx.navigation:navigation-compose:2.4.0")
    implementation("androidx.appcompat:appcompat:1.6.1")

    //FCM Push notification
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.3")

    dependencies {
        implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    }


    //  implementation("org.greenrobot:eventbus:3.3.1")
  //  implementation ("com.android.volley:volley:1.2.1")
   // implementation ("com.google.firebase:firebase-admin:9.0.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")

    // implementation ("androidx.compose.material3:material3-snackbar:1.0.0")
    //  implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0-alpha04")

    implementation ("androidx.security:security-crypto:1.1.0-alpha03")
    implementation("com.google.firebase:firebase-database-ktx:20.2.2")
    implementation("androidx.compose.foundation:foundation-layout-android:1.6.3")
    //  implementation ("com.google.firebase:firebase-database:22.0.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation ("androidx.compose.material:material:1.4.0")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation(platform("com.google.firebase:firebase-bom:32.7.4"))
 //   implementation(platform("com.google.firebase:firebase-bom:32.7.1"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation ("com.google.firebase:firebase-auth-ktx:22.1.2")

    //FCM
    implementation("com.google.firebase:firebase-messaging")
   // implementation ("com.google.firebase:firebase-messaging:23.0.0")

    //FBSTORAGE
    implementation ("com.google.firebase:firebase-storage:20.2.1")
    implementation ("com.google.firebase:firebase-core:19.0.2")


    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}