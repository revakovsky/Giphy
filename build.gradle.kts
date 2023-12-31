buildscript {
    dependencies { }
}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false

    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.ksp) apply false

    alias(libs.plugins.hilt) apply false
}
true // Needed to make the Suppress annotation work for the plugins block