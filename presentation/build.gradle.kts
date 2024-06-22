plugins {
    alias(libs.plugins.moksh.android.library.compose)
    alias(libs.plugins.moksh.android.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.moksh.presentation"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json.jvm)
    testImplementation(libs.junit)
    debugImplementation(libs.androidx.ui.tooling)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(projects.domain)
}