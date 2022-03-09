import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
    application
}

group = "me.stepayurin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://netbeans.apidesign.org/maven2/")
}

dependencies {
    testImplementation(kotlin("test"))// https://mvnrepository.com/artifact/org.netbeans.modules/org-netbeans-lib-profiler
    implementation("org.netbeans.modules:org-netbeans-lib-profiler:RELEASE802")


}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}