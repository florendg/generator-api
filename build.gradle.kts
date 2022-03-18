plugins {
    war
}

repositories {
    mavenCentral()
}

java {
    version = JavaVersion.VERSION_17
}

dependencies {
    implementation("jakarta.platform:jakarta.jakartaee-api:9.1.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
}

tasks {
    test {
        useJUnitPlatform()
    }
}

tasks.create<Copy>("deploy") {
    dependsOn("build")
    from("$buildDir/libs/listGenerator-17.war")
    into("/usr/local/opt/wildfly-26-ee9/standalone/deployments/")
}
