plugins {
    id("java")
    id("maven-publish")
}

val group = "tv.ender"
val artifactId = "Code"
version = "1.0.2"

repositories {
    mavenCentral()
}

dependencies {
    /* junit */
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.3")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0")

    /* lombok */
    implementation("org.projectlombok:lombok:1.18.28")
    annotationProcessor("org.projectlombok:lombok:1.18.28")
}

tasks {
    val stage by registering {
        dependsOn(test, build, clean)
    }

    publish {
        dependsOn(stage)
        doLast {
            println("Published $artifactId-$version to host")
        }
    }

    build {
        mustRunAfter(test, clean)
    }

    test {
        useJUnitPlatform()
    }
}

publishing {
    repositories {
        maven {
            name = "publicEnder"
            url = uri("https://repo.ender.tv/public")
            credentials(PasswordCredentials::class)
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }

    publications {
        create<MavenPublication>("maven") {
            this.groupId = group
            this.artifactId = artifactId
            this.version = version
            from(components["java"])
        }
    }
}
