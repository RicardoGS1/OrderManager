package com.virtualworld.mipymeanabelmaster.screen.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.waterfall
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.compose.rememberNavController
import com.virtualworld.mipymeanabelmaster.navigation.AppNavHost

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    Surface(modifier = Modifier.fillMaxSize()) {

        val navController = rememberNavController()
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()


        Scaffold(
            // bottomBar = { BottomBarView(navController) },
            topBar = { TopBarView(scrollBehavior,navController) },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            contentWindowInsets = WindowInsets.waterfall
        ) { paddingValues ->

           val bottomPadding = paddingValues.calculateBottomPadding() // Reduce 16.dp al margen inferior

            val modifiedPaddingValues = PaddingValues(
                start = paddingValues.calculateStartPadding(LayoutDirection.Ltr),
                top = paddingValues.calculateTopPadding(),
                end = paddingValues.calculateEndPadding(LayoutDirection.Ltr),
                bottom = bottomPadding
            )

            AppNavHost(navController, modifiedPaddingValues)

        }
    }

}