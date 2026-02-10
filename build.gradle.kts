plugins {
    java
    alias(libs.plugins.blossom)
}

repositories {
    mavenCentral()
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    compileOnly(libs.velocity)
    annotationProcessor(libs.velocity)
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(21)
    }
}

sourceSets {
    main {
        blossom {
            javaSources {
                property("version", project.version.toString())
            }
        }
    }
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(21))
