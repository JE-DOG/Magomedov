package ru.je_dog.tinkoff_school.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import retrofit2.Retrofit
import ru.je_dog.core.app.AppBuildConfig
import ru.je_dog.films.di.dependency.ProductComponentDeps

@Component(
    modules = [
        AppModule::class
    ]
)
interface AppComponent: ProductComponentDeps {

    override val context: Context
    override val retrofit: Retrofit
    override val buildConfig: AppBuildConfig

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance
            context: Context
        ): AppComponent

    }
}