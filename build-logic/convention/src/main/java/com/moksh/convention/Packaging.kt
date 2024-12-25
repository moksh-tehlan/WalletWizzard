package com.moksh.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal fun Project.configurePackaging(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    commonExtension.run {
        extensions.configure<ApplicationExtension> {
            packaging {
                resources {
                    excludes += "/META-INF/{AL2.0,LGPL2.1}"
                }
            }
        }
    }
}