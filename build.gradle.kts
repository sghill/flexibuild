plugins {
    application
    java
    alias(libs.plugins.nebula.release)
}

group = "net.sghill.flexibuild"
description = "Run basic Gradle or Maven tasks without knowing which"

application {
    mainClass.set("net.sghill.flexibuild.Main")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

dependencies {
    implementation(platform(libs.log4j.bom))
    implementation(libs.log4j.api)
    implementation(libs.log4j.core)
    implementation(libs.log4j.iostreams)
    implementation(libs.commons.exec)

    testImplementation(libs.assertj)
    testImplementation(libs.jimfs)
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit5.api)
    testImplementation(libs.junit5.params)

    testRuntimeOnly(libs.junit5.engine)
}

dependencyLocking {
    lockAllConfigurations()
}

tasks.test {
    useJUnitPlatform()
}
