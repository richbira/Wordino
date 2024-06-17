plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "it.unimib.wordino"
    compileSdk = 34

    defaultConfig {
        applicationId = "it.unimib.wordino"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    val nav_version = "2.7.7"
    implementation("com.google.firebase:firebase-database:21.0.0")
    implementation("com.google.android.gms:play-services-auth:21.1.1")
    implementation("androidx.security:security-crypto:1.1.0-alpha06")
    implementation ("com.github.mkhan9047:Easy-Checker:1.0.2")
    implementation("androidx.annotation:annotation:1.7.1")
    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-database")
    implementation("com.google.firebase:firebase-storage")
    implementation("androidx.navigation:navigation-fragment:$nav_version")
    implementation("androidx.navigation:navigation-ui:$nav_version")
    implementation("androidx.fragment:fragment:1.2.5")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("commons-validator:commons-validator:1.8.0")
    implementation("androidx.gridlayout:gridlayout:1.0.0")
    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation ("androidx.lifecycle:lifecycle-extensions:2.2.0-beta01")
    implementation ("androidx.viewpager2:viewpager2:1.0.0")

    // Room
    implementation("androidx.room:room-common:2.6.1") // Should also be 2.6.0
    implementation("androidx.room:room-runtime:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}