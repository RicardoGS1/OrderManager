package com.virtualworld.mipymeanabelmaster.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.virtualworld.mipymeanabelmaster.screen.orders.OrdersScreen


@Composable
fun AppNavHost(navController: NavHostController, paddingValues: PaddingValues) {

    NavHost(
        navController,
        startDestination = RouteOrders.route,
        modifier = Modifier.padding(paddingValues)
    ) {

        composable(RouteOrders.route) {
            OrdersScreen()
        }




    }
    }