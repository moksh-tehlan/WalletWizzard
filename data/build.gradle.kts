plugins {
    alias(libs.plugins.moksh.android.library)
    alias(libs.plugins.moksh.android.hilt)
    alias(libs.plugins.moksh.ktor)
}

android {
    namespace = "com.moksh.data"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(projects.domain)
}