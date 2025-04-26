package com.android.build_logic

import com.android.build_logic.extensions.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidKotlinPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            pluginManager.apply("org.jetbrains.kotlin.android")
            configureKotlinAndroid()
        }
    }
}
