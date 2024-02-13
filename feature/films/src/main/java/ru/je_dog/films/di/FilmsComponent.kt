package ru.je_dog.films.di

import dagger.Component
import ru.je_dog.films.di.dependency.ProductComponentDeps
import ru.je_dog.films.presentation.DetailFilmFragment
import ru.je_dog.films.presentation.FilmsFragment
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
    fun inject(fragment: DetailFilmFragment)

    @Component.Factory
    interface Factory{
        fun create(
            deps: ProductComponentDeps
        ): FilmsComponent
    }

}