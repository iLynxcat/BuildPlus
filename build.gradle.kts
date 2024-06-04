import org.apache.tools.ant.filters.ReplaceTokens


plugins {
    java

    kotlin("jvm") version "1.9.24"
}


val kotlinVersion = "1.9.24"
val minecraftVersion = "1.20.4"

group = "me.ilynxcat.mc.build-plus"
version = "0.0.1-alpha.02"

java { toolchain.languageVersion.set(JavaLanguageVersion.of(21)) }

tasks {
    processResources {
        from("src/main/resources") {
            duplicatesStrategy = DuplicatesStrategy.INCLUDE

            include("**/*.yml")
            filter<ReplaceTokens>("tokens" to mapOf(
                "VERSION" to project.version,
                "KOTLIN_VERSION" to kotlinVersion
            ))
        }
    }
}

repositories {
    mavenCentral()

    maven {
        name = "paper"
        url = uri("https://papermc.io/repo/repository/maven-public/")
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    compileOnly("io.papermc.paper:paper-api:$minecraftVersion-R0.1-SNAPSHOT")
}

kotlin { jvmToolchain(21) }
