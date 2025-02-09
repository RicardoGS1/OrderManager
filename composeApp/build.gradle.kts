import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)

    alias(libs.plugins.kotlinxSerialization)

}

kotlin {
    jvm("desktop")
    
    sourceSets {
        val desktopMain by getting
        
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)

            //NAVIGATION
            implementation(libs.androidx.navigation.compose)

            //NETWORK
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.contentnegotiation)
            implementation(libs.kotlinx.serialization.json)

            //FIREBASE
            implementation(libs.gitlive.firebase.firestore)
            implementation(libs.gitlive.firebase.auth)

            //COIL
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)

            //ID
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            //implementation(libs.koin.compose.viewmodel )
            implementation(project.dependencies.platform(libs.koin.bom))




        }


        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)

        }
    }
}




compose.desktop {
    application {
        mainClass = "com.virtualworld.mipymeanabelmaster.MainKt"


        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.virtualworld.mipymeanabelmaster"
            packageVersion = "1.0.0"
        }
    }
}
