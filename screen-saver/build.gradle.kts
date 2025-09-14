import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    val xcf = XCFramework("KlokkSceenSaver")

    val targets = listOf(macosX64(), macosArm64())
    targets.forEach { target ->
        target.binaries.framework {
            binaryOption("bundleId", "com.phaestion.KlokkScreenSaver")
            baseName = "KlokkSceenSaver"
            isStatic = true
            xcf.add(this)
        }
    }

    jvm {
        binaries {
            executable {
                mainClass.set("MainKt")
            }
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.ui)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.components.resources)
            implementation(libs.kotlin.coroutines.core)
            implementation(project(":libklokk"))
        }

        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
        }
    }

    targets.forEach {
        it.binaries {
            executable {
                entryPoint = "compose.main"
            }
        }
    }
}
