package com.android.build_logic.extensions

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.configureComposeAndroid() {
    val extension = androidExtension

    extension.buildFeatures {
        compose = true
    }

    extension.composeOptions {
        kotlinCompilerExtensionVersion =
            libs.findVersion("androidxComposeCompiler").get().toString()
    }

    dependencies {
        val bom = libs.findLibrary("androidx-compose-bom").get()
        add("implementation", platform(bom))
        add("androidTestImplementation", platform(bom))
        add("testImplementation", libs.findLibrary("junit").get())
        add("implementation", libs.findLibrary("androidx.material3").get())
        add("implementation", libs.findLibrary("androidx.ui").get())
        add("implementation", libs.findLibrary("androidx.ui.tooling.preview").get())
        add("androidTestImplementation", libs.findLibrary("androidx.junit").get())
        add("androidTestImplementation", libs.findLibrary("androidx.ui.test.junit4").get())
        add("androidTestImplementation", libs.findLibrary("androidx.espresso.core").get())
        add("debugImplementation", libs.findLibrary("androidx.ui.tooling").get())
        add("debugImplementation", libs.findLibrary("androidx.ui.test.manifest").get())
    }
}

