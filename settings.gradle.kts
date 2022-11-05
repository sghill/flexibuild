rootProject.name = "flexibuild"

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            library("assertj", "org.assertj", "assertj-core").version {
                strictly("[3.23.1, 4.0.0[")
            }
            library("commons-exec", "org.apache.commons", "commons-exec").version {
                strictly("[1.3, 2.0[")
            }
            library("jimfs", "com.google.jimfs", "jimfs").version {
                strictly("[1.2, 2.0[")
            }
            library("junit-bom", "org.junit", "junit-bom").version {
                strictly("[5.9.1, 6.0.0[")
            }
            library("junit5-api", "org.junit.jupiter", "junit-jupiter-api").withoutVersion()
            library("junit5-engine", "org.junit.jupiter", "junit-jupiter-engine").withoutVersion()
            library("junit5-params", "org.junit.jupiter", "junit-jupiter-params").withoutVersion()
            
            library("log4j-bom", "org.apache.logging.log4j", "log4j-bom").version {
                strictly("[2.19.0, 3.0.0[")
            }
            library("log4j-api", "org.apache.logging.log4j", "log4j-api").withoutVersion()
            library("log4j-core", "org.apache.logging.log4j", "log4j-core").withoutVersion()
            library("log4j-iostreams", "org.apache.logging.log4j", "log4j-iostreams").withoutVersion()
            
            plugin("nebula.release", "com.netflix.nebula.release").version { 
                strictly("[17.0.1, 18.0.0[")
            }
        }
    }
}
