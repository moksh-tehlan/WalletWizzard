import com.android.build.api.dsl.ApplicationExtension
import com.moksh.convention.ExtensionType
import com.moksh.convention.configureBuildTypes
import com.moksh.convention.configureKotlinAndroid
import com.moksh.convention.configurePackaging
import com.moksh.convention.configureProductFlavors
import com.moksh.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }
            extensions.configure<ApplicationExtension> {
                defaultConfig {
                    applicationId = libs.findVersion("projectApplicationId").get().toString()
                    targetSdk = libs.findVersion("projectTargetSdkVersion").get().toString().toInt()
                    versionCode = libs.findVersion("projectVersionCode").get().toString().toInt()
                    versionName = libs.findVersion("projectVersionName").get().toString()
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    vectorDrawables {
                        useSupportLibrary = true
                    }
                }
                configureKotlinAndroid(this)
                configureBuildTypes(this, ExtensionType.APPLICATION)
                configureProductFlavors(this, ExtensionType.APPLICATION)
                configurePackaging(this)
            }
            dependencies {
                "implementation"(libs.findLibrary("timber").get())
            }
        }
    }
}
