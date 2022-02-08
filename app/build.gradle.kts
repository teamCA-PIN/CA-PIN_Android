import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.gradle.internal.impldep.com.jcraft.jsch.ConfigRepository.defaultConfig
import java.util.Properties
import java.io.FileInputStream
import extensions.implementation
import extensions.kapt

plugins {
    id(BuildPlugins.ANDROID_APPLICATION)
    id(BuildPlugins.KOTLIN_ANDROID)
    id(BuildPlugins.KOTLIN_KAPT)
    id(BuildPlugins.HILT_PLUGIN)
    id(BuildPlugins.NAVIGATION_SAFE_ARGS)
    id(BuildPlugins.PARCELIZE)
}

val properties = Properties()
properties.load(FileInputStream(rootProject.file("local.properties")))

android {
    defaultConfig {
        applicationId = BuildAndroidConfig.APPLICATION_ID
        compileSdk = BuildAndroidConfig.COMPILE_SDK_VERSION
        buildToolsVersion = BuildAndroidConfig.BUILD_TOOLS_VERSION
        targetSdk = BuildAndroidConfig.TARGET_SDK_VERSION
        minSdk = BuildAndroidConfig.MIN_SDK_VERSION
        versionCode = BuildAndroidConfig.VERSION_CODE
        versionName = BuildAndroidConfig.VERSION_NAME

        testInstrumentationRunner = BuildAndroidConfig.TEST_INSTRUMENTATION_RUNNER
        buildConfigField("String", "NAVER_MAP_CLIENT_ID", properties["NAVER_MAP_CLIENT_ID"] as String)
        buildConfigField("String", "BASE_URL", properties["BASE_URL"] as String)

        signingConfigs {
            register("release") {
                storeFile = file("capinkeystores.jks")
                storePassword = gradleLocalProperties(rootDir).getProperty("CAPIN_KEYSTORE_PASSWORD")
                keyAlias = gradleLocalProperties(rootDir).getProperty("CAPIN_ALIAS")
                keyPassword = gradleLocalProperties(rootDir).getProperty("CAPIN_ALIAS_PASSWORD")
            }
        }
    }

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
        }

        getByName("debug") {
            buildConfigField("String", "NAVER_MAP_CLIENT_ID", properties["NAVER_MAP_CLIENT_ID"] as String)
            buildConfigField("String", "BASE_URL", properties["BASE_URL"] as String)
        }
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    dependencies {
        implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
        implementation(Dependencies.KOTLIN)
        implementation(Dependencies.CORE_KTX)
        implementation(Dependencies.APPCOMPAT)
        implementation(Dependencies.MATERIAL)
        implementation(Dependencies.CONSTRAIN_LAYOUT)
        implementation(Dependencies.CARD_VIEW)
        implementation(Dependencies.VIEW_PAGER)
        implementation(Dependencies.RECYCLER_VIEW)

        implementation(Dependencies.FRAGMENT_KTX)
        implementation(Dependencies.LOTTIE)

        implementation(Dependencies.GLIDE)
        kapt(Dependencies.GLIDE_COMPILER)

        implementation(Dependencies.RETROFIT)
        implementation(Dependencies.RETROFIT_CONVERTER)
        implementation(Dependencies.RETROFIT_RX_ADAPTER)
        implementation(Dependencies.OKHTTP)
        implementation(Dependencies.OKHTTP_LOGGING_INTERCEPTOR)

        implementation(Dependencies.NAVIGATION_FRAGMENT)
        implementation(Dependencies.NAVIGATION_UI)

        implementation(Dependencies.LIFECYCLE_LIVEDATA)
        implementation(Dependencies.LIFECYCLE_VIEWMODEL)
        implementation(Dependencies.LIFECYCLE_COMMON_JAVA)
        kapt(Dependencies.LIFECYCLE_COMPILER)

        implementation(Dependencies.RXJAVA)
        implementation(Dependencies.RX_ANDROID)

        implementation(Dependencies.ROOM)
        kapt(Dependencies.ROOM_COMPILER)

        implementation(Dependencies.HILT)
        kapt(Dependencies.HILT_COMPILER)

        implementation(Dependencies.NAVER_MAP)
        implementation(Dependencies.CRYPTO)

        implementation(Dependencies.SHIMMER)
        implementation(Dependencies.GMS_LOCATION)
        implementation(Dependencies.FLEX_BOX)

        testImplementation(TestDependencies.JUNIT)
        androidTestImplementation(TestDependencies.EXT)
        androidTestImplementation(TestDependencies.ESPRESSO)
    }
}
