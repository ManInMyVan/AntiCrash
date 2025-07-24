plugins {
    id("net.kyori.blossom") version "1.3.2"
}

allprojects {
    apply(plugin = "net.kyori.blossom")

    val modid: String by project

    blossom {
        replaceToken("@MOD_VERSION@", version)
        replaceToken("@MOD_NAME@", rootProject.name)
        replaceToken("@MOD_ID@", modid)
    }

    repositories {
        mavenCentral()
        maven("https://repo.spongepowered.org/maven/")
        maven("https://pkgs.dev.azure.com/djtheredstoner/DevAuth/_packaging/public/maven/v1")
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
        maven("https://maven.meteordev.org/releases")
    }

    dependencies {
        compileOnly("org.projectlombok:lombok:1.18.38")
        annotationProcessor("org.projectlombok:lombok:1.18.38")
    }
}
