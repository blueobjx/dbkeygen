plugins {
    id("java")
    id("com.gradleup.shadow") version "9.0.1"
}

group = "blueobjx"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("info.picocli:picocli:4.7.7")
    implementation("org.hibernate:hibernate-core:5.6.15.Final")
}

tasks.jar {
    manifest {
        attributes(
            mapOf(
                "Main-Class" to "dbkeygen.DBKeyGen"
            )
        )
    }
}

tasks.shadowJar {
    include("META-INF/MANIFEST.MF")
    include("dbkeygen/**")
    include("picocli/CommandLine**.class")
    include("org/hibernate/boot/model/naming/Identifier.class")
    include("org/hibernate/boot/model/naming/NamingHelper**.class")
    include("org/hibernate/boot/model/naming/IllegalIdentifierException.class")
    include("org/hibernate/HibernateException.class")
    include("org/hibernate/internal/util/StringHelper.class")
    include("javax/persistence/PersistenceException.class")

    minimize()
}

val createShellScriptTask = tasks.register("createShellScript") {
    dependsOn("build", "shadowJar")

    doLast {
        val execShell = layout.buildDirectory.file("libs/${rootProject.name}").get().asFile
        execShell.writeText("#!/bin/sh\n\nexec java -jar \"\$0\" \"\$@\"\n\n")
        execShell.appendBytes(tasks.shadowJar.get().outputs.files.singleFile.readBytes())
        execShell.setExecutable(true)
    }
}

tasks.register<Tar>("dist") {
    dependsOn(createShellScriptTask)

    compression = Compression.GZIP

    archiveBaseName.set(rootProject.name)
    archiveVersion.set(version.toString())
    archiveExtension.set("tar.gz")

    from(projectDir) {
        include("README.md")
    }

    from(layout.buildDirectory.dir("libs")) {
        include("**/${rootProject.name}")
        filePermissions { unix("rwxr-xr-x") }
    }

    into("${rootProject.name}-${version}")
}