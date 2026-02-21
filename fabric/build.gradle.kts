import me.modmuss50.mpp.ModPublishExtension
import net.fabricmc.loom.task.prod.ServerProductionRunTask

plugins {
    packetevents.`library-conventions`
    packetevents.`publish-conventions`
    alias(libs.plugins.fabric.loom)
}

repositories {
    maven("https://repo.codemc.io/repository/maven-snapshots/")
}

val minecraft_version: String by project
val loader_version: String by project

dependencies {
    api(libs.bundles.adventure)
    api(project(":api", "shadow"))
    api(project(":netty-common"))

    include(libs.bundles.adventure)
    include(project(":api", "shadow"))
    include(project(":netty-common"))

    // To change the versions, see the gradle.properties file
    minecraft("com.mojang:minecraft:$minecraft_version")
    compileOnly("net.fabricmc:fabric-loader:$loader_version")
}

tasks {
    withType<JavaCompile> {
        options.release = 25
    }

    jar {
        destinationDirectory = rootProject.layout.buildDirectory.dir("libs")
        archiveClassifier = ""
    }
}

loom {
    splitEnvironmentSourceSets()
    mods {
        register("packetevents") {
            sourceSet(sourceSets.main.get())
            sourceSet(sourceSets.maybeCreate("client"))
        }
    }
    accessWidenerPath = sourceSets.main.get().resources.srcDirs.single()
        .resolve("${rootProject.name}.accesswidener")
}

configure<ModPublishExtension> {
    file = tasks.named<Jar>("jar").flatMap { it.archiveFile }
    additionalFiles.from(tasks.named<Jar>("sourcesJar").flatMap { it.archiveFile })
}

tasks.register<ServerProductionRunTask>("prodServer") {
    javaLauncher = javaToolchains.launcherFor {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}
