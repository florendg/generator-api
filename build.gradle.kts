plugins {
    war
//    `jvm-test-suite`
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion = libs.versions.jvmToolChain.map {
            JavaLanguageVersion.of(it)
        }
        vendor = JvmVendorSpec.ADOPTIUM
    }
}

dependencies {
    implementation("jakarta.platform:jakarta.jakartaee-api:10.0.0")
    implementation("org.postgresql:postgresql:42.2.27")
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter("5.9.0")
        }
    }
}

tasks.create<Copy>("deploy") {
    dependsOn("build")
    from("${layout.buildDirectory}/libs/listGenerator.war")
    into("/usr/local/opt/wildfly-as/standalone/deployments/")
}
