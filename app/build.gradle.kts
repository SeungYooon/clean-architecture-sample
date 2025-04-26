plugins {
    id("my.android.application")
    id("my.android.compose")
    id("my.android.hilt")
}

android {
    namespace = "com.android.pokemon"
}

dependencies {
    implementation(project(":core"))
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":feature:list"))
    implementation(project(":feature:favorite"))
    implementation(project(":feature:detail"))

    testImplementation(libs.junit)

    implementation(libs.hilt.navigation.compose)
}