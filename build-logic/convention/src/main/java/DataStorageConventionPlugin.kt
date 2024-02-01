import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import ru.je_dog.build_logic.convention.core.ext.applyIfNotFind
import ru.je_dog.build_logic.convention.core.ext.implementation
import ru.je_dog.build_logic.convention.core.ext.implementationProject
import ru.je_dog.build_logic.convention.core.ext.kapt
import ru.je_dog.build_logic.convention.core.ext.versionCatalog
import ru.je_dog.build_logic.convention.dependencies.DependenciesName

class DataStorageConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) = with(target) {

        val libs = versionCatalog()

        with(pluginManager){
            applyIfNotFind("je_dog.android.library")
            applyIfNotFind("org.jetbrains.kotlin.kapt")
        }
        dependencies {
            implementationProject(":core:data:storage")
            with(DependenciesName){
                with(libs){
                    //Room
                    implementation(findLibrary(room))
                    implementation(findLibrary(room_ktx))
                    kapt(findLibrary(room_compiler))
                }
            }
        }
    }
}