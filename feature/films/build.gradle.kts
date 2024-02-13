import ru.je_dog.build_logic.convention.core.ext.implementationProject

plugins {
    id("je_dog.feature")
    id("je_dog.android.xml")
}

android {
    namespace = "ru.je_dog.products"
}

dependencies {
    implementationProject(":data:films")
    ""
    implementation(libs.coil.core)
}