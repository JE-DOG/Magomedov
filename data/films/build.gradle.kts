plugins {
    id("je_dog.android.library")
}

android {
    namespace = "ru.je_dog.tinkoff_school.data.products"
}

dependencies {
    api(project(":data:films:storage"))
    api(project(":data:films:network"))
    api(project(":core:data"))
    api(project(":core:data:storage"))
    api(project(":core:data:network"))
}