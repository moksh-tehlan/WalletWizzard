plugins {
    alias(libs.plugins.moksh.android.application.compose)
    alias(libs.plugins.moksh.android.hilt)
}

android {
    namespace = "com.moksh.walletwizzard"
}

dependencies {

    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.lifecycle.runtime.ktx)
//    implementation(libs.androidx.activity.compose)
//    implementation(platform(libs.androidx.compose.bom))
//    implementation(libs.androidx.ui)
//    implementation(libs.androidx.ui.graphics)
//    implementation(libs.androidx.ui.tooling.preview)
//    implementation(libs.androidx.material3)
    implementation(libs.androidx.security.crypto.ktx)
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
//    androidTestImplementation(platform(libs.androidx.compose.bom))
//    androidTestImplementation(libs.androidx.ui.test.junit4)
//    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(projects.presentation)
    implementation(projects.data)
    implementation(projects.domain)
    testImplementation("junit:junit:4.12")
}