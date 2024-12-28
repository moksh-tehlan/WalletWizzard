plugins {
    alias(libs.plugins.moksh.android.library)
    alias(libs.plugins.moksh.android.hilt)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.moksh.android.firebase)
}

android {
    namespace = "com.moksh.data"
}

dependencies {
    // Core dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.junit.ktx)
    implementation(projects.domain)

    // Room dependencies
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    annotationProcessor(libs.androidx.room.compiler)
    ksp(libs.androidx.room.compiler)

    // Unit Testing dependencies
    testImplementation(libs.bundles.testing)

    // Instrumentation Testing dependencies
    androidTestImplementation(libs.bundles.androidTesting)
}