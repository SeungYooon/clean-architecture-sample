package com.android.build_logic
import com.android.build_logic.extensions.configureHiltAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidHiltPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            configureHiltAndroid()
        }
    }
}