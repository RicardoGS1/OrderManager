package com.virtualworld.mipymeanabelmaster.screen.main

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarView(scrollBehavior: TopAppBarScrollBehavior) {



    val currentTitle =  "Anabella´s Shop"


        CenterAlignedTopAppBar(
            title = { Text(text = currentTitle) },
            scrollBehavior = scrollBehavior,
            navigationIcon = {
//                if (currentRoute == RouteDetail.route + "/{productId}") {
//                    IconButton(onClick = { navController.popBackStack() }) {
//                        Icon(
//                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                            contentDescription = "Back"
//                        )
//                    }
//                }
            }
        )


}