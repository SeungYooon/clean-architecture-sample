package com.android.build_logic.extensions

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.configureAndroidRoom() {
    with(pluginManager) {
        apply("org.jetbrains.kotlin.kapt")
    }

    dependencies {
        "implementation"(libs.findLibrary("room-runtime").get())
        "implementation"(libs.findLibrary("room-ktx").get())
        "kapt"(libs.findLibrary("room-compiler").get())
    }
}