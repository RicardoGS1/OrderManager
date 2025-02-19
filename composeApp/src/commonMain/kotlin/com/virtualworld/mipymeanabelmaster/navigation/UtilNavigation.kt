package com.virtualworld.mipymeanabelmaster.navigation

import androidx.navigation.NavController



fun NavController.navigateToDetailDestination(screen: String) {

    navigate(screen) {
        saveState()
    }

}

