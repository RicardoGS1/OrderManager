package com.virtualworld.mipymeanabelmaster.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.virtualworld.mipymeanabelmaster.screen.details.DetailsScreen
import com.virtualworld.mipymeanabelmaster.screen.orders.OrdersScreen
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf


@Composable
fun AppNavHost(navController: NavHostController, paddingValues: PaddingValues) {

    NavHost(
        navController,
        startDestination = RouteOrders.route,
        modifier = Modifier.padding(paddingValues)
    ) {

        val onOrderClicked: (String, String) -> Unit = { orderId, uid ->
            navController.navigateToDetailDestination(orderId, uid)
        }

        composable(RouteOrders.route) {
            OrdersScreen(viewModel = koinViewModel(), onOrderClicked = onOrderClicked)
        }

        composable(
            RouteDetails.route + "/{orderId}/{uid}",
            arguments = listOf(
                navArgument("orderId") { type = NavType.StringType },
                navArgument("uid") { type = NavType.StringType })
        ) { navBackStackEntry ->

            val orderId = navBackStackEntry.arguments?.getString("orderId")
            val uid = navBackStackEntry.arguments?.getString("uid")
            DetailsScreen(viewModel = koinViewModel(parameters = { parametersOf(orderId, uid) }))
        }


    }
}