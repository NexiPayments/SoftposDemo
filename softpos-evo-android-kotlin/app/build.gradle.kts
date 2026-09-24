plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "it.nexi.softpos_evo_kotlin_01"
    compileSdk = 35


    defaultConfig {
        applicationId = "it.nexi.softpos_evo_kotlin_01"
        minSdk = 24
        targetSdk = 35
        versionCode = 3
        versionName = "1.7"


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
        viewBinding = true
    }
}


dependencies {

    // start - implementation custom
    implementation(files("libs/app2app-1.3.3-release.aar"))
    implementation(files("libs/commons-io-2.5.jar"))

    implementation("com.google.code.gson:gson:2.12.1")
    implementation ("org.slf4j:slf4j-api:1.7.25")
    implementation("com.github.tony19:logback-android:2.0.0")

    // end - implementation custom

    implementation("com.github.instacart:truetime-android:4.0.0.alpha")
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.nimbusds:nimbus-jose-jwt:9.39.3")

    // start -  Coroutines
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.x.x")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.x.x")
    // end -  Coroutines

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)

}