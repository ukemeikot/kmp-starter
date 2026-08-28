plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.kmp.library) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.compose.multiplatform) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.room) apply false
}

// The Kotlin/Wasm toolchain pulls an npm dependency tree for the webpack dev
// server, locked in kotlin-js-store/wasm/yarn.lock. Those packages are build-time
// only and never reach the shipped app, but they still raise Dependabot alerts,
// and Dependabot cannot patch a Gradle-managed lockfile itself.
//
// The wasm target has its own Yarn root (WasmYarnPlugin), distinct from the
// Kotlin/JS one -- a resolution registered on the JS YarnRootExtension silently
// does nothing here. After changing a pin, regenerate the lockfile with:
//
//     ./gradlew kotlinWasmUpgradeYarnLock
//
plugins.withType<org.jetbrains.kotlin.gradle.targets.wasm.yarn.WasmYarnPlugin> {
    the<org.jetbrains.kotlin.gradle.targets.wasm.yarn.WasmYarnRootExtension>().apply {
        // GHSA-96hv-2xvq-fx4p memory-exhaustion DoS, plus an uninitialised
        // memory disclosure in the same package.
        resolution("ws", "8.21.0")
    }
}
