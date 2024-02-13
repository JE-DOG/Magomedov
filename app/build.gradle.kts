import ru.je_dog.build_logic.convention.core.ext.implementationProject

plugins {
    id("je_dog.android.application")
    id("je_dog.android.xml")
}

android {
    namespace = "ru.je_dog.tinkoff_school"

    defaultConfig {
        applicationId = "ru.je_dog.tinkoff_school"
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
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementationProject(":feature:films")
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.convertor.gson)
    implementation(libs.okhttp.interceptor)
    implementation(libs.okhttp.core)
}