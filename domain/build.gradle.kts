plugins {
    alias(libs.plugins.moksh.android.library)
    alias(libs.plugins.moksh.android.hilt)
}

android {
    namespace = "com.moksh.domain"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    testImplementation(libs.mockito.kotlin)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}