package ru.je_dog.tinkoff_school

import android.app.Application
import ru.je_dog.films.di.dependency.ProductsComponentDepsStore
import ru.je_dog.tinkoff_school.di.AppComponent
import ru.je_dog.tinkoff_school.di.DaggerAppComponent

class App: Application() {

    val component: AppComponent by lazy {
        DaggerAppComponent.factory()
            .create(this)
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        initDi()
    }

    private fun initDi() = with(component) {

        ProductsComponentDepsStore.deps = this
    }

    companion object {

        lateinit var INSTANCE: App
            private set

    }
}