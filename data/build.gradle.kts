plugins {
    alias(libs.plugins.moksh.android.library)
    alias(libs.plugins.moksh.android.hilt)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.com.google.devtools.ksp)
}

android {
    namespace = "com.moksh.data"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.junit.ktx)
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    debugImplementation(libs.truth)

    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    testImplementation(libs.androidx.room.testing)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    implementation(projects.domain)
}