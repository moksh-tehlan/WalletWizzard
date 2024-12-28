package com.moksh.convention

import org.gradle.api.Project
import java.util.Properties
import java.io.FileInputStream
import java.io.IOException

object EnvConfigUtil {
    fun loadProperties(project: Project, flavor: String): Properties? {
        if (flavor.isBlank()) {
            return null
        }

        return loadEnvProperties(project, ".env/.$flavor")
    }

    private fun loadEnvProperties(project: Project, filename: String): Properties? {

        val envFile = project.rootProject.file(filename)

        // If file doesn't exist or isn't readable, return null
        if (!envFile.isFile || !envFile.canRead()) {
            return null
        }

        return Properties().apply {
            try {
                FileInputStream(envFile).use { inputStream ->
                    load(inputStream)
                }
            } catch (e: IOException) {
                // If there's any error reading the file, return null
                return null
            }
        }
    }
}