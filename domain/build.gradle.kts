plugins {
    id("kotlin")
    alias(libs.plugins.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}

dependencies {

    implementation(libs.hilt.inject)
    implementation(libs.coroutines.core)

}