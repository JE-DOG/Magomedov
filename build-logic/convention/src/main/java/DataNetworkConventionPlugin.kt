import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import ru.je_dog.build_logic.convention.core.ext.applyIfNotFind
import ru.je_dog.build_logic.convention.core.ext.implementation
import ru.je_dog.build_logic.convention.core.ext.implementationProject
import ru.je_dog.build_logic.convention.core.ext.kapt
import ru.je_dog.build_logic.convention.core.ext.versionCatalog
import ru.je_dog.build_logic.convention.dependencies.DependenciesName

class DataNetworkConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) = with(target) {

        val libs = versionCatalog()

        with(pluginManager){
            applyIfNotFind("je_dog.android.library")
        }
        dependencies {
            implementationProject(":core:data:network")
            implementationProject(":core:data")
            with(DependenciesName){
                with(libs){
                    //Network
                    implementation(findLibrary(retrofit_core))
                    implementation(findLibrary(retrofit_convertor_gson))
                    implementation(findLibrary(okhttp_interceptor))
                }
            }
        }
    }
}