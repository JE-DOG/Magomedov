package ru.je_dog.products.di

import dagger.Component
import ru.je_dog.products.di.dependency.ProductComponentDeps
import ru.je_dog.products.presentation.FilmsFragment
import ru.je_dog.tinkoff_school.data.films.di.FilmsDataModule

@Component(
    modules = [
        FilmsDataModule::class
    ],
    dependencies = [
        ProductComponentDeps::class
    ]
)
interface FilmsComponent {

    fun inject(fragment: FilmsFragment)

    @Component.Factory
    interface Factory{
        fun create(
            deps: ProductComponentDeps
        ): FilmsComponent
    }

}