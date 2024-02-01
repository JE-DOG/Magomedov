plugins {
    id("je_dog.android.library")
}

android {
    namespace = "ru.je_dog.storage"
}

dependencies {
    with(libs.room){
        implementation(this)
        implementation(ktx)
//        kapt(compiler)
    }
}