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
    debugImplementation(libs.androidx.ui.test.junit4.android)
    debugImplementation(libs.ui.test.manifest)
    debugImplementation(libs.truth)
    testImplementation(libs.junit)

//    testImplementation(libs.junit)
    implementation(libs.androidx.ui.tooling)
//    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(projects.domain)
}