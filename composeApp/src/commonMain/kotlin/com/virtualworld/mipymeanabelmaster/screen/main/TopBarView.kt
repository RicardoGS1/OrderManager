package com.virtualworld.mipymeanabelmaster.screen.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.virtualworld.mipymeanabelmaster.navigation.RouteDetails

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarView(scrollBehavior: TopAppBarScrollBehavior, navController: NavHostController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val currentTitle =  "Anabella´s Shop"


        CenterAlignedTopAppBar(
            title = { Text(text = currentTitle) },
            scrollBehavior = scrollBehavior,
            navigationIcon = {
                if (currentRoute == RouteDetails.route + "/{orderId}") {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            }
        )


}