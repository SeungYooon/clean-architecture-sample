plugins {
    id("my.android.library")
    id("my.android.kotlin")
}

android {
    namespace = "com.android.common"
}

dependencies {
    implementation(project(":core"))
    implementation(project(":domain"))
}