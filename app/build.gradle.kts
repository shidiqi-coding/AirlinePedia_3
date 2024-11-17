plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")

}


android {
    namespace = "com.dicoding.airlinepedia"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.dicoding.airlinepedia"
        minSdk = 24
        targetSdk = 34
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

    viewBinding {
        enable = true
    }

}

//repositories {
//    google()
//    mavenCentral()  // Pastikan ini ada
//    jcenter()
//}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("de.hdodenhof:circleimageview:3.1.0")
//    implementation ("com.github.chrisbanes:photoview:2.4.0")
//    implementation (com.github.chrisbanes:PhotoView:2.3.0')
}

