plugins {
    java
    id("io.qameta.allure") version "2.12.0"
}



group = "com.ust.sdet"
version = "0.1.0"


val seleniumVersion = "4.45.0"
val selenideVersion = "7.16.2"
val junitVersion = "5.13.4"
val cucumberVersion = "7.34.3"
val allureVersion = "2.33.0"
val extentVersion = "5.1.2"
val extentCucumberAdapterVersion = "1.14.0"
val slf4jVersion = "2.0.17"
val testcontainersVersion = "2.0.5"
val flywayVersion = "10.22.0"
val postgresqlVersion = "42.7.4"

// Java
java {
    sourceCompatibility = JavaVersion.VERSION_22
    targetCompatibility = JavaVersion.VERSION_22
}

dependencies {
    // BOMs
    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation(platform("io.cucumber:cucumber-bom:$cucumberVersion"))
    testImplementation(platform("io.qameta.allure:allure-bom:$allureVersion"))
    testImplementation(platform("org.testcontainers:testcontainers-bom:$testcontainersVersion"))

    // Selenium
    testImplementation("org.seleniumhq.selenium:selenium-java:$seleniumVersion")
    testImplementation("com.codeborne:selenide:$selenideVersion")

    // JUnit
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.platform:junit-platform-suite")

    // Cucumber
    testImplementation("io.cucumber:cucumber-java")
    testImplementation("io.cucumber:cucumber-junit-platform-engine")
    testImplementation("io.cucumber:cucumber-picocontainer")

    // Reporting
    testImplementation("io.qameta.allure:allure-junit5")
    testImplementation("io.qameta.allure:allure-cucumber7-jvm")
    testImplementation("com.aventstack:extentreports:$extentVersion")
    testImplementation("tech.grasshopper:extentreports-cucumber7-adapter:$extentCucumberAdapterVersion")

    // Logging
    testImplementation("org.slf4j:slf4j-simple:$slf4jVersion")

    // Database
    testImplementation("org.flywaydb:flyway-core:$flywayVersion")
    testImplementation("org.flywaydb:flyway-database-postgresql:$flywayVersion")
    testImplementation("org.postgresql:postgresql:$postgresqlVersion")

    // Testcontainers
    testImplementation("org.testcontainers:testcontainers-junit-jupiter:$testcontainersVersion")
    testImplementation("org.testcontainers:testcontainers-postgresql:$testcontainersVersion")
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.release.set(22)
}

// Common configuration for all test tasks
tasks.withType<Test>().configureEach {

    useJUnitPlatform()

    systemProperty(
        "baseUrl",
        providers.gradleProperty("baseUrl")
            .orElse("http://localhost:5173")
            .get()
    )

    systemProperty(
        "headless",
        providers.gradleProperty("headless")
            .orElse("false")
            .get()
    )

    systemProperty(
        "browser",
        providers.gradleProperty("browser")
            .orElse("chrome")
            .get()
    )

    systemProperty(
        "build.label",
        providers.gradleProperty("buildLabel")
            .orElse("gradle-local")
            .get()
    )

    systemProperty("cucumber.publish.quiet", "true")

    testLogging {
        events("passed", "skipped", "failed")
        showStandardStreams = true
        exceptionFormat =
            org.gradle.api.tasks.testing.logging.TestExceptionFormat.SHORT
    }
}

fun Test.useProjectTestClasses() {
    testClassesDirs = sourceSets.test.get().output.classesDirs
    classpath = sourceSets.test.get().runtimeClasspath
}


tasks.test {

    description = "Runs all Selenium UI regression tests."

    group = "verification"

    include(
        "**/PlaceOrderE2ETest.class",
        "**/CartTotalTest.class"
    )

    maxParallelForks = 1
}


val e2eTest by tasks.registering(Test::class) {

    description = "Runs the complete End-to-End purchase workflow."

    group = "verification"

    useProjectTestClasses()

    include("**/PlaceOrderE2ETest.class")

    maxParallelForks = 1
}


val functionalTest by tasks.registering(Test::class) {

    description = "Runs functional UI validation tests."

    group = "verification"

    useProjectTestClasses()

    include("**/CartTotalTest.class")

    maxParallelForks = 1
}


val parallelStructureTest by tasks.registering(Test::class) {

    description = "Demonstrates Gradle parallel execution."

    group = "verification"

    useProjectTestClasses()

    include("**/CartTotalTest.class")

    maxParallelForks =
        Runtime.getRuntime().availableProcessors().coerceAtMost(2)
}


val integrationTest by tasks.registering(Test::class) {

    description = "Runs database integration tests."

    group = "verification"

    useProjectTestClasses()

    include("**/OrderRepositoryIntegrationTest.class")

    maxParallelForks = 1
}


val cucumberSmoke by tasks.registering(Test::class) {

    description = "Runs Cucumber smoke scenarios."

    group = "verification"

    useProjectTestClasses()

    include("**/RunCucumberTest.class")

    systemProperty("cucumber.filter.tags", "@smoke")

    maxParallelForks = 1
}

tasks.register("projectBuildSummary") {

    description = "Displays the Gradle command reference."

    group = "help"

    doLast {

        println(
            """
                PROJECT COMMAND REFERENCE
                Build Project
                -------------
                ./gradlew clean build
                
                Compile Test Classes
                --------------------
                ./gradlew testClasses
                
                Run All UI Tests
                ----------------
                ./gradlew test
                
                Run End-to-End Test
                -------------------
                ./gradlew e2eTest --no-configuration-cache
                
                Run Functional Test
                -------------------
                ./gradlew functionalTest --no-configuration-cache
                
                Run Integration Test
                --------------------
                ./gradlew integrationTest --no-configuration-cache
                
                Run Cucumber Smoke Tests
                ------------------------
                ./gradlew cucumberSmoke -Pheadless=true --no-configuration-cache
                
                ========================================================
            """.trimIndent()
        )
    }
}