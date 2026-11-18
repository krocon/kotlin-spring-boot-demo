// Folge 1: kleine Sprachbeispiele, unabhängig von Spring.
// Start: ./gradlew -q intro:dataClasses | intro:nullSafety | intro:features
plugins {
    kotlin("jvm")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

repositories {
    mavenCentral()
}

listOf("DataClasses", "NullSafety", "Features").forEach { demo ->
    tasks.register<JavaExec>(demo.replaceFirstChar { it.lowercase() }) {
        group = "intro"
        classpath = sourceSets["main"].runtimeClasspath
        mainClass = "intro.${demo}Kt"
    }
}
