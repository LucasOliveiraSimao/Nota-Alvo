import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

val localPropertiesFile = rootProject.file("local.properties")
val localProperties = Properties()
localProperties.load(FileInputStream(localPropertiesFile))

val keystorePropertiesFile = rootProject.file("app/keystore.properties")
val keystoreProperties = Properties()
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

android {
    namespace = "com.lucassimao.notaalvo"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.lucassimao.notaalvo"
        minSdk = 26
        targetSdk = 35
        versionCode = 37
        versionName = "3.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(
            "String",
            "ADMOB_APP_ID_TEST",
            localProperties.getProperty("ADMOB_APP_ID_TEST")
        )

        buildConfigField(
            "String",
            "ADMOB_APP_ID_PROD",
            localProperties.getProperty("ADMOB_APP_ID_PROD")
        )

    }

    signingConfigs {
        create("release") {
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            storeFile = file(keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["storePassword"] as String
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation("com.airbnb.android:lottie:3.4.0")

    implementation("io.insert-koin:koin-android:3.3.0")

    implementation("com.google.android.gms:play-services-ads:22.6.0")
    implementation("com.google.android.gms:play-services-ads-lite:22.6.0")

    implementation("com.github.AppIntro:AppIntro:6.3.1")

    implementation(platform("com.google.firebase:firebase-bom:32.1.0"))

    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-config:22.1.2")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    androidTestImplementation("com.adevinta.android:barista:4.2.0") {
        exclude(group = "org.jetbrains.kotlin")
    }

    testImplementation("androidx.arch.core:core-testing:2.2.0")


    testImplementation("io.mockk:mockk:1.13.5")
    testImplementation("io.mockk:mockk-android:1.13.5")
}

tasks.register("generateProjectTree") {
    group = "documentation"
    description = "Gera um arquivo com a árvore de diretórios da pasta 'notaalvo'."

    val outputFile = layout.buildDirectory.file("projectTree.txt")

    doLast {
        outputFile.get().asFile.printWriter().use { writer ->

            fun walk(dir: File, prefix: String = "") {
                dir.listFiles()?.sortedBy { it.name }?.forEach { file ->
                    writer.println("$prefix${if (file.isDirectory) "📁" else "📄"} ${file.name}")
                    if (file.isDirectory) {
                        walk(file, "$prefix    ")
                    }
                }
            }

            val targetDir = file("src/main/java/com/lucassimao/notaalvo")

            if (targetDir.exists()) {
                writer.println("📁 notaalvo")
                walk(targetDir, "    ")
            } else {
                println("Diretório alvo não encontrado: ${targetDir.path}")
            }
        }

        println("Árvore de pastas gerada em: ${outputFile.get().asFile.path}")
    }
}