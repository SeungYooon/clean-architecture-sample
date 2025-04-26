package com.android.build_logic

import com.android.build_logic.extensions.configureAndroidRoom
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidRoomPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            configureAndroidRoom()
        }
    }
}