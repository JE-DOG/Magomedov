import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import ru.je_dog.build_logic.convention.core.ext.applyIfNotFind
import ru.je_dog.build_logic.convention.core.ext.implementation
import ru.je_dog.build_logic.convention.core.ext.implementationProject
import ru.je_dog.build_logic.convention.core.ext.kapt
import ru.je_dog.build_logic.convention.core.ext.versionCatalog
import ru.je_dog.build_logic.convention.dependencies.DependenciesName

class KotlinLibraryConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) = with(target) {

        val libs = versionCatalog()

        with(pluginManager){
            applyIfNotFind("org.jetbrains.kotlin.jvm")
            applyIfNotFind("org.jetbrains.kotlin.kapt")
        }
        dependencies {
            implementationProject(":core")
            with(libs){
                with(DependenciesName){
                    implementation(findLibrary(coroutines_core))
                    //Dagger
                    implementation(findLibrary(dagger_core))
                    kapt(findLibrary(dagger_compiler))
                }
            }
        }

    }
}