package com.moksh.convention

import com.android.build.api.dsl.ProductFlavor
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.ApplicationProductFlavor
import org.gradle.api.Project
import java.util.Properties

internal fun Project.configureProductFlavors(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    extensionType: ExtensionType
) {
    commonExtension.apply {
        flavorDimensions += "environment"

        productFlavors {
            namespace = "com.moksh.walletwizzard"

            create("development") {
                val devProps = EnvConfigUtil.loadProperties(project, "development")
                dimension = "environment"
                configureDevelopmentFlavor(extensionType,devProps)
            }

            create("production") {
                val prodProps = EnvConfigUtil.loadProperties(project,"production")
                dimension = "environment"
                configureProductionFlavor(prodProps)
            }
        }
    }
}

private fun ProductFlavor.configureDevelopmentFlavor(extensionType: ExtensionType, devProps: Properties) {
    buildConfigField("String", "BASE_URL", devProps.getProperty("BASE_URL"))
    buildConfigField("Boolean", "ENABLE_LOGGING", devProps.getProperty("ENABLE_LOGGING"))

    if (this is ApplicationProductFlavor && extensionType == ExtensionType.APPLICATION) {
        applicationIdSuffix = ".dev"
        versionNameSuffix = "-dev"
    }
}

private fun ProductFlavor.configureProductionFlavor(prodProps: Properties) {
    buildConfigField("String", "BASE_URL", prodProps.getProperty("BASE_URL"))
    buildConfigField("Boolean", "ENABLE_LOGGING", prodProps.getProperty("ENABLE_LOGGING"))
}