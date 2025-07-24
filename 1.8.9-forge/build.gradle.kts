plugins {
    idea
    java
    id("dev.architectury.architectury-pack200") version "0.1.3"
    alias(libs.plugins.loom)
    alias(libs.plugins.shadow)
}

val minecraftVersion: String = ext["minecraft-version"] as String
val forgeVersion: String = ext["forge-version"] as String
val modid: String by project
val accessTransformerFile = file("src/main/resources/${modid}_at.cfg")

dependencies {
    minecraft("com.mojang:minecraft:$minecraftVersion")
    mappings("de.oceanlabs.mcp:mcp_stable:22-$minecraftVersion")
    forge("net.minecraftforge:forge:$minecraftVersion-$forgeVersion")

    shadow("org.spongepowered:mixin:0.7.11-SNAPSHOT") {
        isTransitive = false
    }

    modRuntimeOnly("me.djtheredstoner:DevAuth-forge-legacy:1.2.1")
}

loom {
    runConfigs {
        named("client") {
            isIdeConfigGenerated = true
        }
        remove(getByName("server"))
    }
    forge {
        pack200Provider.set(dev.architectury.pack200.java.Pack200Adapter())
        mixinConfig("mixins.${modid}.json")
        if (accessTransformerFile.exists()) {
            accessTransformer(accessTransformerFile)
        }
    }
    mixin {
        defaultRefmapName.set("mixins.${modid}.refmap.json")
    }
}

sourceSets.main {
    output.setResourcesDir(sourceSets.main.flatMap { it.java.classesDirectory })
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(ext["java-version"] as String))

tasks.compileJava {
    options.encoding = "UTF-8"
}

tasks.processResources {
    rename("${modid}_at.cfg", "META-INF/${modid}_at.cfg")
}

tasks.withType<org.gradle.jvm.tasks.Jar>().configureEach {
    archiveClassifier.set("")
    archiveBaseName.set(modid)
    archiveAppendix.set(project.name)
    exclude("META-INF/versions/**")
}

tasks.jar {
    manifest.attributes.run {
        this["FMLCorePluginContainsFMLMod"] = "true"
        this["ForceLoadAsMod"] = "true"

        this["TweakClass"] = "org.spongepowered.asm.launch.MixinTweaker"
        this["MixinConfigs"] = "mixins.$modid.json"
        if (accessTransformerFile.exists())
            this["FMLAT"] = "${modid}_at.cfg"
    }
}

tasks.shadowJar {
    dependsOn(tasks.jar)
    configurations = project.configurations.shadow.map { listOf(it) }.get()
}

tasks.remapJar {
    dependsOn(tasks.shadowJar)
    inputFile.set(tasks.shadowJar.get().archiveFile)
}

tasks.assemble.get().dependsOn(tasks.remapJar)
