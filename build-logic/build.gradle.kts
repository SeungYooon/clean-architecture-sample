plugins {
    `kotlin-dsl`
}
dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "my.android.application"
            implementationClass = "com.android.build_logic.AndroidApplicationPlugin"
        }
        register("androidLibrary") {
            id = "my.android.library"
            implementationClass = "com.android.build_logic.AndroidLibraryPlugin"
        }
        register("androidKotlin") {
            id = "my.android.kotlin"
            implementationClass = "com.android.build_logic.AndroidKotlinPlugin"
        }
        register("androidCompose") {
            id = "my.android.compose"
            implementationClass = "com.android.build_logic.AndroidComposePlugin"
        }
        register("androidHilt") {
            id = "my.android.hilt"
            implementationClass = "com.android.build_logic.AndroidHiltPlugin"
        }
        register("androidRoom") {
            id = "my.android.room"
            implementationClass = "com.android.build_logic.AndroidRoomPlugin"
        }
    }
}
