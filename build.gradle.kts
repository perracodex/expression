/*
 * Copyright (c) 2024-Present Perracodex. Use of this source code is governed by an MIT license.
 */

plugins {
    alias(libs.plugins.kotlin.jvm) // Required for Kotlin JVM development.
    alias(libs.plugins.detekt) // Required for static code analysis.
}

group = "io.github.perracodex"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    detektPlugins(libs.detekt.formatting)
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}