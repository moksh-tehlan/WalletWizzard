package com.moksh.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.BuildType
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.internal.scope.ProjectInfo.Companion.getBaseName
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import java.io.FileInputStream
import java.util.Properties

internal fun Project.configureBuildTypes(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    extensionType: ExtensionType
) {
    commonExtension.run {
        buildFeatures {
            buildConfig = true
        }

        signingConfigs {
            create("production") {
                EnvConfigUtil.loadProperties(project, "production")?.let { props ->
                    storeFile = props.getProperty("STORE_FILE")?.let { file(it) }
                    storePassword = props.getProperty("STORE_PASSWORD")
                    keyAlias = props.getProperty("KEY_ALIAS")
                    keyPassword = props.getProperty("KEY_PASSWORD")
                }
            }
            create("development") {
                EnvConfigUtil.loadProperties(project, "development")?.let { props ->
                    storeFile = props.getProperty("STORE_FILE")?.let { file(it) }
                    storePassword = props.getProperty("STORE_PASSWORD")
                    keyAlias = props.getProperty("KEY_ALIAS")
                    keyPassword = props.getProperty("KEY_PASSWORD")
                }
            }
        }

        when (extensionType) {
            ExtensionType.APPLICATION -> {
                extensions.configure<ApplicationExtension> {
                    buildTypes {
                        debug {
                            signingConfig = signingConfigs.getByName("development")
                            configureDebugBuildType()
                        }
                        release {
                            isShrinkResources  = true
                            isMinifyEnabled = true
                            signingConfig = signingConfigs.getByName("production")
                            configureReleaseBuildType(commonExtension)
                        }
                    }
                }
            }

            ExtensionType.LIBRARY -> {
                extensions.configure<LibraryExtension> {
                    buildTypes {
                        debug {
                            configureDebugBuildType()
                        }
                        release {
                            configureReleaseBuildType(commonExtension)
                        }
                    }
                }
            }
        }
    }
}

private fun BuildType.configureDebugBuildType() {
}

private fun BuildType.configureReleaseBuildType(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    proguardFiles(
        commonExtension.getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
    )
}