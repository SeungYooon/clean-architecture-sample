plugins {
    id("my.android.library")
    id("my.android.kotlin")
    id("my.android.room")
    id("my.android.hilt")
    id("my.android.compose")
}

android {
    namespace = "com.android.data"
}

dependencies {
    implementation(project(":core"))
    implementation(project(":domain"))

    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    implementation(libs.androidx.paging.common)
}