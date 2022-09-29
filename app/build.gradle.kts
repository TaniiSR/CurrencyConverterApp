import java.util.*

plugins {
    id(BuildPluginsConfig.androidApplication)
    id(BuildPluginsConfig.androidHilt)
    kotlin(BuildPluginsConfig.kotlinAndroid)
    kotlin(BuildPluginsConfig.kotlinKapt)
    id(BuildPluginsConfig.kotlinParcelize)
    id("kotlin-android")
}

android {
    compileSdk = (BuildAndroidConfig.COMPILE_SDK_VERSION)

    defaultConfig {
        applicationId = BuildAndroidConfig.APPLICATION_ID
        minSdk = BuildAndroidConfig.MIN_SDK_VERSION
        targetSdk = BuildAndroidConfig.TARGET_SDK_VERSION
        versionCode = BuildAndroidConfig.VERSION_CODE
        versionName = BuildAndroidConfig.VERSION_NAME

        testInstrumentationRunner = BuildAndroidConfig.androidTestInstrumentation
        val remoteProperties = File(project.rootDir, "buildSrc/remote.properties")
        val properties = Properties()
        if (remoteProperties.exists()) {
            remoteProperties.inputStream().use { properties.load(it) }
            try {
                if (properties.containsKey("BASE_URL")) {
                    buildConfigField(
                        "String",
                        "BASE_URL",
                        properties["BASE_URL"].toString()
                    )
                }
                if (properties.containsKey("API_KEY")) {
                    buildConfigField(
                        "String",
                        "API_KEY",
                        properties["API_KEY"].toString()
                    )
                }
            } catch (e: Exception) {
                System.err.println(e.printStackTrace())
            }
        }
    }

    signingConfigs {
        create("release") {

        }

        getByName("debug") {
        }
    }

    buildTypes {
        getByName("debug") {
            signingConfig = signingConfigs.getByName("debug")
            isMinifyEnabled = false
            isDebuggable = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

        }
        getByName("release") {
            isMinifyEnabled = true
            isDebuggable = false
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    flavorDimensions.addAll(listOf("version"))
    productFlavors {
        create("live") {
            versionCode = 1
            versionName = "1.0.0"
            applicationId = BuildAndroidConfig.APPLICATION_ID
            dimension = "version"
            resValue("string", "app_name", "Currency App")
        }

        create("dev") {
            versionCode = 1
            versionName = "1.0.0"
            applicationIdSuffix = ".dev"
            dimension = "version"
            resValue("string", "app_name", "Currency App")
        }

    }
    viewBinding {
        android.buildFeatures.viewBinding = true
    }

    packagingOptions {
        resources {
            excludes += "LICENSE.txt"
            excludes += "META-INF/DEPENDENCIES"
            excludes += "META-INF/ASL2.0"
            excludes += "META-INF/AL2.0"
            excludes += "META-INF/LGPL2.1"
            excludes += "META-INF/NOTICE"
            excludes += "META-INF/*.kotlin_module"
            excludes += "META-INF/gradle/incremental.annotation.processors"
            resources.excludes += "DebugProbesKt.bin"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(DependenciesManager.kotlinImplementation)
    implementation(DependenciesManager.lifeCycleKtxImplementation)
    implementation(DependenciesManager.androidxImplementation)
    implementation(DependenciesManager.hiltImplementation)
    implementation(DependenciesManager.navigationImplementation)
    implementation(DependenciesManager.networkImplementation)
    implementation(DependenciesManager.roomImplementation)
//    implementation(AndroidxDependencies.WORK_MANAGER)
//    implementation(AndroidxDependencies.WORK_MANAGER_HILT)
    testImplementation(DependenciesManager.testingImplementation)
    implementation(DependenciesManager.thirdPartyImplementation)
    androidTestImplementation(DependenciesManager.androidTestImplementation)
    kapt(HiltDaggerDependencies.DAGGER_COMPILER)
    kapt(HiltDaggerDependencies.HILT_COMPILER)
    kapt(RoomDependencies.ROOM_COMPILER)
    implementation(TestDependencies.COROUTINES)
    implementation(TestDependencies.ANDROIDX_ARCH_CORE)
    annotationProcessor(NetworkDependencies.GLIDE_COMPILER)
}
// Allow references to generated code
kapt {
    correctErrorTypes = true
}