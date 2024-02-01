import ru.je_dog.build_logic.convention.core.ext.implementationProject

plugins {
    id("je_dog.android.library")
}

android {
    namespace = "ru.je_dog.tinkoff_school.data.products"
}

dependencies {
    implementationProject(":data:products:storage")
    implementationProject(":data:products:network")
}