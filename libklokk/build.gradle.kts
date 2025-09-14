@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

group = "com.theapache64"
version = "1.0.1"

kotlin {
    macosX64()
    macosArm64()

    listOf(js(), wasmJs()).forEach {
        it.browser()
        it.binaries.library()
    }

    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(compose.ui)
            implementation(compose.runtime)
            implementation(compose.material)
            implementation(compose.materialIconsExtended)
            implementation(libs.kotlinx.datetime)
        }
    }
}
