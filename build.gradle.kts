plugins {
    id("java")
    `maven-publish`
}

val group = "tv.ender"
val artifactId = "Code"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.3")

    implementation("org.projectlombok:lombok:1.18.28")
    annotationProcessor("org.projectlombok:lombok:1.18.28")
}

tasks {
    val stage by registering {
        dependsOn(test, build, clean)
    }

    build {
        mustRunAfter(test, clean)
    }

    test {
        useJUnitPlatform()
    }
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
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
            this.version = version
            from(components["java"])
        }
    }
}