import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension
import ru.je_dog.build_logic.convention.Configuration
import ru.je_dog.build_logic.convention.core.ext.androidTestImplementation
import ru.je_dog.build_logic.convention.core.ext.applyIfNotFind
import ru.je_dog.build_logic.convention.core.ext.implementation
import ru.je_dog.build_logic.convention.core.ext.implementationProject
import ru.je_dog.build_logic.convention.core.ext.kapt
import ru.je_dog.build_logic.convention.core.ext.testImplementation
import ru.je_dog.build_logic.convention.core.ext.versionCatalog
import ru.je_dog.build_logic.convention.dependencies.DependenciesName

class AndroidApplicationConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) = with(target) {

        val libs = versionCatalog()

        with(pluginManager){
            applyIfNotFind("com.android.application")
            applyIfNotFind("org.jetbrains.kotlin.android")
            applyIfNotFind("org.jetbrains.kotlin.kapt")
        }

        extensions.configure<ApplicationExtension> {
            compileSdk = Configuration.compileSdk

            defaultConfig {
                minSdk = Configuration.minSdk
                targetSdk = Configuration.targetSdk
                versionCode = Configuration.versionCode
                versionName = Configuration.versionName

                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

                vectorDrawables {
                    useSupportLibrary = true
                }
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }

            kotlinExtension.jvmToolchain(17)

            packaging {
                resources {
                    excludes += "/META-INF/{AL2.0,LGPL2.1}"
                }
            }
            buildFeatures {
                viewBinding = true
            }
        }
        dependencies {
            implementationProject(":core")
            implementationProject(":core:data:network")
            implementationProject(":core:data:storage")
            implementationProject(":core:feature")
            with(libs){
                with(DependenciesName){
                    testImplementation(findLibrary(jUnit))
                    androidTestImplementation(findLibrary(jUnitExtensionAndroidx))
                    //Dagger
                    implementation(findLibrary(dagger_android))
                    kapt(findLibrary(dagger_compiler))
                }
            }
        }
    }
}