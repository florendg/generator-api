plugins {
    war
}

repositories {
    mavenCentral()
}

java {
    version = JavaVersion.VERSION_19
}

dependencies {
    implementation("jakarta.platform:jakarta.jakartaee-api:10.0.0")
    implementation("org.postgresql:postgresql:42.2.27")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.0")
}

tasks {
    test {
        useJUnitPlatform()
    }
}

tasks.create<Copy>("deploy") {
    dependsOn("build")
    from("$buildDir/libs/listGenerator-19.war")
    into("/usr/local/opt/wildfly-as/standalone/deployments/")
}
