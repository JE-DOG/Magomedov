plugins {
    id("je_dog.android.library")
}

android {
    namespace = "ru.je_dog.network"
}

dependencies {
    with(libs){
        with(okhttp){
            implementation(interceptor)
        }
        with(retrofit){
            implementation(core)
            implementation(convertor.gson)
        }
    }
}