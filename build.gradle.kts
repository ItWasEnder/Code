plugins {
    id("java")
    id("maven-publish")
}

val group = "tv.ender"
val artifactId = "Code"
version = "1.0.1"

repositories {
    mavenCentral()
}

dependencies {
    /* junit */
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.3")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.3")

    /* lombok */
    implementation("org.projectlombok:lombok:1.18.28")
    annotationProcessor("org.projectlombok:lombok:1.18.28")
}

tasks {
    val stage by registering {
        dependsOn(test, build, clean)
    }

    val publishSnapshot by registering {
        dependsOn(stage, "publishAllPublicationsToSnapshotsDragonRepository")
        doLast {
            println("Published $artifactId-$version-SNAPSHOT to snapshots")
        }
    }

    val publishRelease by registering {
        dependsOn(stage, "publishAllPublicationsToReleasesDragonRepository")
        doLast {
            println("Published $artifactId-$version to releases")
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
            name = "ReleasesDragon"
            url = uri("https://repo.ender.tv/releases")
            credentials(PasswordCredentials::class)
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
        maven {
            name = "SnapshotsDragon"
            url = uri("https://repo.ender.tv/snapshots")
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
            this.version = if (project.tasks["publishSnapshot"].enabled) {
                "$version-SNAPSHOT"
            } else {
                version
            }
            from(components["java"])
        }
    }
}
