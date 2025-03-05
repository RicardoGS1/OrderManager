package com.virtualworld.mipymeanabelmaster.navigation

import androidx.navigation.NavController
import androidx.navigation.NavHostController


fun NavHostController.navigateToDetailDestination(orderId: String, uid: String) {
    this.navigate(RouteDetails.route + "/$orderId/$uid")
 {
        saveState()
    }

}

