plugins {
    id("my.android.library")
    id("my.android.kotlin")
    id("my.android.hilt")
    id("my.android.compose")
}

android {
    namespace = "com.android.list"
}

dependencies {
    implementation(project(":core"))
    implementation(project(":domain"))
    implementation(project(":feature:common"))

    implementation(libs.androidx.paging.compose.android)
    implementation(libs.coil.compose)
    implementation(libs.androidx.navigation.compose)
}