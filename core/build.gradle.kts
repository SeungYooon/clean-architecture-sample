plugins {
    id("my.android.library")
    id("my.android.kotlin")
    id("my.android.compose")
}
android {
    namespace = "com.android.core"
}

dependencies {
    implementation(libs.androidx.navigation.compose)
}