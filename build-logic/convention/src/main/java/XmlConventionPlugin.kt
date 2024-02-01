import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import ru.je_dog.build_logic.convention.core.ext.androidTestImplementation
import ru.je_dog.build_logic.convention.core.ext.implementation
import ru.je_dog.build_logic.convention.core.ext.versionCatalog
import ru.je_dog.build_logic.convention.dependencies.DependenciesName

class XmlConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        val libs = versionCatalog()

        dependencies {
            with(libs){
                with(DependenciesName){
                    implementation(findLibrary(androidxCoreKtx))
                    implementation(findLibrary(androidxAppcompat))
                    implementation(findLibrary(xml_material2))
                    implementation(findLibrary(xml_constraint_layout))
                    androidTestImplementation(findLibrary(espressoCore))
                }
            }
        }
    }
}