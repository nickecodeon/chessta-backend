plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation ("org.xerial:sqlite-jdbc:3.41.2.1")
    implementation ("org.springframework.boot:spring-boot-starter-data-jpa:3.1.3")
}

tasks.test {
    useJUnitPlatform()
}