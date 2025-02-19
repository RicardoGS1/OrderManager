package com.virtualworld.mipymeanabelmaster.screen.details

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun DetailsScreen(viewModel: DetailsViewModel) {

    val orderState by viewModel.orderState.collectAsStateWithLifecycle()


    Text(orderState.toString())

}