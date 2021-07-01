import java.util.Properties
import java.io.FileInputStream

plugins {
    id(BuildPlugins.ANDROID_APPLICATION)
    id(BuildPlugins.KOTLIN_ANDROID)
    id(BuildPlugins.KOTLIN_KAPT)
    id(BuildPlugins.HILT_PLUGIN)
    id(BuildPlugins.NAVIGATION_SAFE_ARGS)
    id(BuildPlugins.PARCELIZE)
}

val properties = Properties()
properties.load(FileInputStream(rootProject.file("./local.properties")))

android {
    compileSdkVersion(BuildAndroidConfig.COMPILE_SDK_VERSION)

    defaultConfig {
        applicationId = BuildAndroidConfig.APPLICATION_ID
        targetSdkVersion(BuildAndroidConfig.TARGET_SDK_VERSION)
        minSdkVersion(BuildAndroidConfig.MIN_SDK_VERSION)
        buildToolsVersion(BuildAndroidConfig.BUILD_TOOLS_VERSION)

        versionCode = BuildAndroidConfig.VERSION_CODE
        versionName = BuildAndroidConfig.VERSION_NAME

        testInstrumentationRunner = BuildAndroidConfig.TEST_INSTRUMENTATION_RUNNER
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
        }
    }
    buildFeatures {
        dataBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    dependencies {
        implementation (Dependencies.KOTLIN)
        implementation (Dependencies.CORE_KTX)
        implementation (Dependencies.APPCOMPAT)
        implementation (Dependencies.MATERIAL)
        implementation (Dependencies.CONSTRAIN_LAYOUT)
        testImplementation (TestDependencies.JUNIT)
        androidTestImplementation (TestDependencies.EXT)
        androidTestImplementation (TestDependencies.ESPRESSO)

        implementation (Dependencies.FRAGMENT_KTX)
        implementation(Dependencies.LOTTIE)

        implementation(Dependencies.GLIDE)
        kapt(Dependencies.GLIDE_COMPILER)

        implementation(Dependencies.RETROFIT)
        implementation(Dependencies.RETROFIT_CONVERTER)
        implementation(Dependencies.RETROFIT_RX_ADAPTER)
        implementation(Dependencies.OKHTTP)

        implementation(Dependencies.NAVIGATION_FRAGMENT)
        implementation(Dependencies.NAVIGATION_UI)

        implementation(Dependencies.HILT)
        kapt(Dependencies.HILT_COMPILER)

        implementation(Dependencies.LIFECYCLE_LIVEDATA)
        implementation(Dependencies.LIFECYCLE_VIEWMODEL)
        kapt(Dependencies.LIFECYCLE_COMPILER)

        implementation(Dependencies.RXJAVA)
        implementation(Dependencies.RX_ANDROID)

        implementation(Dependencies.ROOM)
        kapt(Dependencies.ROOM_COMPILER)

    }
}

