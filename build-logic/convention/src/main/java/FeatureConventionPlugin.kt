import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import ru.je_dog.build_logic.convention.core.ext.androidTestImplementation
import ru.je_dog.build_logic.convention.core.ext.applyIfNotFind
import ru.je_dog.build_logic.convention.core.ext.implementation
import ru.je_dog.build_logic.convention.core.ext.implementationProject
import ru.je_dog.build_logic.convention.core.ext.testImplementation
import ru.je_dog.build_logic.convention.core.ext.versionCatalog
import ru.je_dog.build_logic.convention.dependencies.DependenciesName

class FeatureConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) = with(target) {

        val libs = versionCatalog()

        with(pluginManager){
            applyIfNotFind("je_dog.android.library")
            applyIfNotFind("org.jetbrains.kotlin.kapt")
        }

        dependencies {
            implementationProject(":core")
            implementationProject(":core:data:storage")
            implementationProject(":core:data:network")
            with(libs){
                with(DependenciesName){
                    androidTestImplementation(findLibrary(espressoCore))
                }
            }

        }

    }
}