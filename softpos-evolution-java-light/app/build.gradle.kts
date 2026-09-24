plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "it.nexi.softpos_evo_java_light"
    compileSdk = 35

    defaultConfig {
        applicationId = "it.nexi.softpos_evo_java_light"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0 sdk-1.3.6 release"

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

    buildFeatures {
        viewBinding = true
    }

    packaging {
        resources.excludes.add("META-INF/versions/9/OSGI-INF/MANIFEST.MF")
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
    implementation("com.google.code.gson:gson:2.12.1")
    implementation ("org.slf4j:slf4j-api:1.7.25")
    implementation("com.github.tony19:logback-android:2.0.0")

    // Inizio - copiate da PizzaPay

    // -- start library for Nexi SDK --
    implementation("com.github.instacart:truetime-android:4.0.0.alpha")
//    implementation("com.nimbusds:nimbus-jose-jwt:9.22")
    // -- end library for Nexi SDK --

    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    // Fine - copiate da PizzaPay

    // ** Inizio - Crittografia JWK
    implementation("com.nimbusds:nimbus-jose-jwt:9.39.3")
    implementation("org.bouncycastle:bcprov-jdk18on:1.80")

    // Per evitare errore di compilazione delle bouncy-castel
    // "bcpkix-jdk18on 1.78.1: 3 files found with path 'META-INF/versions/9/OSGI-INF/MANIFEST.MF"
    //
    // inserire sopra in questo file la seguente direttiva :
    // packaging {
    //    resources.excludes.add("META-INF/versions/9/OSGI-INF/MANIFEST.MF")
    // }
    implementation("org.bouncycastle:bcpkix-jdk18on:1.80")
    // ** Fine - Crittografia JWK

    implementation("com.fasterxml.jackson.core:jackson-databind:2.18.3")

    implementation("androidx.lifecycle:lifecycle-viewmodel:2.6.1")
    implementation("androidx.lifecycle:lifecycle-livedata:2.6.1")

    // Inserita per poter utilizzare l'sdk in versione debug
    implementation("com.localebro:okhttpprofiler:1.0.8")

    implementation(files("libs/app2app-1.3.6-release.aar"))
    implementation(files("libs/commons-io-2.5.jar"))



}