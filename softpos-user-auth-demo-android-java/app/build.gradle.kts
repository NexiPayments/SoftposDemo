plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "it.nexi.softposbase_java_light_01"
    compileSdk = 34

    defaultConfig {
        applicationId = "it.nexi.softposbase_java_light_01"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"


    }

    buildFeatures {
        viewBinding = true
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation ("org.slf4j:slf4j-api:1.7.25")
    implementation("com.github.tony19:logback-android:2.0.0")
}