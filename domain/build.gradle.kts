plugins {
    id("my.android.library")
    id("my.android.kotlin")
}

android {
    namespace = "com.android.domain"
}

dependencies {
    implementation(libs.javax.inject)
    implementation(libs.androidx.paging.common)
}