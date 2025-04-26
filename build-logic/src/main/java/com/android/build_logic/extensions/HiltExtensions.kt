package com.android.build_logic.extensions

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.configureHiltAndroid() {
    with(pluginManager) {
        apply("dagger.hilt.android.plugin")
        apply("org.jetbrains.kotlin.kapt")
    }

    dependencies {
        "implementation"(libs.findLibrary("hilt.android").get())
        "kapt"(libs.findLibrary("hilt.android.compiler").get())

        "implementation"(libs.findLibrary("hilt.navigation.compose").get())
    }
}