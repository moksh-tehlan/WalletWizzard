package com.moksh.convention

import org.gradle.api.Project
import java.util.Properties
import java.io.FileInputStream

object EnvConfigUtil {
    fun loadProperties(project: Project, flavor: String): Properties {
        return loadEnvProperties(project,".env/.$flavor")
    }

    private fun loadEnvProperties(project: Project, filename: String): Properties {
        val envFile = project.rootProject.file(filename)
        return Properties().apply {
            if (envFile.exists()) {
                load(FileInputStream(envFile))
            }
        }
    }
}