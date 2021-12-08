import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent.*
import org.jetbrains.dokka.Platform.common

plugins {
  signing
  `maven-publish`
  id("shipshape")
  idea
  id("com.google.devtools.ksp") version "1.6.0-1.0.1"
  kotlin("multiplatform") version "1.6.0"
  kotlin("jupyter.api") version "0.11.0-16"
}

val generatedSourcesPath = file("src/jvmMain/kotlin/gen")
idea.module {
  generatedSourceDirs.add(generatedSourcesPath)
}

kotlin {
  jvm {
    tasks {
      build { dependsOn("genShapes") }

      processJupyterApiResources {
        libraryProducers = listOf("ai.hypergraph.kotlingrad.notebook.Integration")
      }

      create<Jar>("javadocJar") {
        dependsOn(dokkaJavadoc)
        archiveClassifier.set("javadoc")
        from(dokkaJavadoc.get().outputDirectory)
      }

      dokkaJavadoc {
        dokkaSourceSets {
          create("commonMain") {
            displayName.set("common")
            platform.set(common)
          }
        }
      }

      named<Test>("jvmTest") {
        minHeapSize = "1024m"
        maxHeapSize = "4096m"
        useJUnitPlatform()
        testLogging {
          events = setOf(
            FAILED,
            PASSED,
            SKIPPED,
            STANDARD_OUT
          )
          exceptionFormat = FULL
          showExceptions = true
          showCauses = true
          showStackTraces = true
          showStandardStreams = true
        }
      }
    }

    // https://stackoverflow.com/a/66352905/1772342

    val signingKeyId = providers.gradleProperty("signing.gnupg.keyId")
    val signingKeyPassphrase = providers.gradleProperty("signing.gnupg.passphrase")
    signing {
      useGpgCmd()
      if (signingKeyId.isPresent && signingKeyPassphrase.isPresent) {
        useInMemoryPgpKeys(signingKeyId.get(), signingKeyPassphrase.get())
        sign(extensions.getByType<PublishingExtension>().publications)
      } else {
        logger.info("PGP signing key not defined, skipping signing configuration")
      }
    }

    publishing {
      publications.create<MavenPublication>("default") {
//        from(components["java"])
//        artifact(tasks["sourcesJar"])
//        artifact(tasks["javadocJar"])

        pom {
          name.set("Kotlin∇")
          description.set("Differentiable Functional Programming with Algebraic Data Types")
          url.set("https://github.com/breandan/kotlingrad")
          licenses {
            license {
              name.set("The Apache Software License, Version 1.0")
              url.set("http://www.apache.org/licenses/LICENSE-3.0.txt")
              distribution.set("repo")
            }
          }
          developers {
            developer {
              id.set("Breandan Considine")
              name.set("Breandan Considine")
              email.set("bre@ndan.co")
              organization.set("Université de Montréal")
            }
          }
          scm {
            url.set("https://github.com/breandan/kotlingrad")
          }
        }
      }
    }
  }

  sourceSets {
    val commonMain by getting {
      // TODO: Maybe move this into the plugin somehow?
      kotlin.srcDir(generatedSourcesPath)
      dependencies {
        implementation(kotlin("stdlib-common"))
        implementation(kotlin("reflect"))
        api("ai.hypergraph:kaliningraph:0.1.9")
      }
    }

    val jvmMain by getting {
      dependencies {
        implementation(kotlin("bom"))
        implementation(kotlin("stdlib"))

        implementation("org.graalvm.js:js:21.3.0")
        implementation("guru.nidi:graphviz-kotlin:0.18.1")

        implementation(kotlin("reflect"))
      }
    }

    val jvmTest by getting {
      dependencies {
        implementation("org.jetbrains.bio:viktor:1.2.0")

        // Property-based testing

        val ejmlVersion = "0.41"
        implementation("org.ejml:ejml-kotlin:$ejmlVersion")
        implementation("org.ejml:ejml-all:$ejmlVersion")

        val kotestVersion = "5.0.0"
        implementation("io.kotest:kotest-runner-junit5:$kotestVersion")
        implementation("io.kotest:kotest-assertions-core:$kotestVersion")
        implementation("io.kotest:kotest-property:$kotestVersion")
        implementation("org.junit.jupiter:junit-jupiter:5.8.2")

        // Symbolic fuzzing interpreter
        implementation(kotlin("scripting-jsr223"))
      }
    }

    val commonTest by getting {
      dependencies {
        implementation(kotlin("test"))
        implementation(kotlin("test-common"))
        implementation(kotlin("test-annotations-common"))
      }
    }
  }
}