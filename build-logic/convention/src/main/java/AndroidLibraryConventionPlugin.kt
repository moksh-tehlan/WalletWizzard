import com.android.build.api.dsl.LibraryExtension
import com.moksh.convention.ExtensionType
import com.moksh.convention.configureBuildTypes
import com.moksh.convention.configureKotlinAndroid
import com.moksh.convention.configureProductFlavors
import com.moksh.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }
            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                configureBuildTypes(this, ExtensionType.LIBRARY)
                configureProductFlavors(this, ExtensionType.LIBRARY)

                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    consumerProguardFiles("consumer-rules.pro")
                }
            }
            dependencies {
                "implementation"(libs.findLibrary("timber").get())
            }
        }
    }
}