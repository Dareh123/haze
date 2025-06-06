// Copyright 2023, Christopher Banes and the Haze project contributors
// SPDX-License-Identifier: Apache-2.0


plugins {
  id("dev.chrisbanes.android.library")
  id("dev.chrisbanes.kotlin.multiplatform")
  id("dev.chrisbanes.compose")
  id("io.github.takahirom.roborazzi")
}

android {
  namespace = "dev.chrisbanes.haze.internal.screenshot"
}

kotlin {
  jvm()
  androidTarget {
    publishLibraryVariants("release")
  }

  sourceSets {
    val commonMain by getting {
      dependencies {
        api(projects.internal.contextTest)

        api(compose.components.resources)
        api(compose.foundation)
        api(compose.material3)
      }
    }

    val commonJvmMain by creating {
      dependsOn(commonMain)

      dependencies {
        api(libs.roborazzi.core)
      }
    }

    androidMain {
      dependsOn(commonJvmMain)

      dependencies {
        implementation(libs.androidx.test.ext.junit)
        implementation(libs.androidx.compose.ui.test.manifest)

        implementation(compose.desktop.uiTestJUnit4)

        implementation(libs.robolectric)

        implementation(libs.roborazzi.compose)
        implementation(libs.roborazzi.junit)
      }
    }

    jvmMain {
      dependsOn(commonJvmMain)

      dependencies {
        implementation(compose.desktop.currentOs)
        implementation(compose.desktop.uiTestJUnit4)
        implementation(libs.roborazzi.composedesktop)
      }
    }
  }

  targets.configureEach {
    compilations.configureEach {
      compileTaskProvider {
        compilerOptions {
          freeCompilerArgs.add("-Xexpect-actual-classes")
        }
      }
    }
  }
}
