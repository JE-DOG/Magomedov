package ru.je_dog.products.di

import dagger.Component
import ru.je_dog.products.di.dependency.ProductComponentDeps
import ru.je_dog.products.presentation.ProductFragment
import ru.je_dog.tinkoff_school.data.di.ProductsDataModule

@Component(
    modules = [
        ProductsDataModule::class
    ],
    dependencies = [
        ProductComponentDeps::class
    ]
)
interface ProductComponent {

    fun inject(fragment: ProductFragment)

    @Component.Factory
    interface Factory{
        fun create(
            deps: ProductComponentDeps
        ): ProductComponent
    }

}