package com.virtualworld.mipymeanabelmaster

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.virtualworld.mipymeanabelmaster.id.initKoin
import com.virtualworld.mipymeanabelmaster.screen.main.MainScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication

@Composable
@Preview
fun App() {


    MaterialTheme {

        val darkTheme = isSystemInDarkTheme()
        val colorScheme = if (!darkTheme) darkColorScheme() else lightColorScheme()

        MaterialTheme(colorScheme = colorScheme) {

            MainScreen()

        }

    }
}