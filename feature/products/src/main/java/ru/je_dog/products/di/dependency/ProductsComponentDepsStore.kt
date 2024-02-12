package ru.je_dog.products.di.dependency

import kotlin.properties.Delegates

object ProductsComponentDepsStore: ProductsComponentDepsProvider {

    override var deps: ProductComponentDeps by Delegates.notNull()

}