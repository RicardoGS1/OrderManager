package com.virtualworld.mipymeanabelmaster.screen.orders

import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.virtualworld.mipymeanabelmaster.core.NetworkResponseState
import com.virtualworld.mipymeanabelmaster.core.Product
import com.virtualworld.mipymeanabelmaster.core.dto.Order

@Composable
fun OrdersScreen(viewModel: OrdersViewModel) {

    val ordersSent by viewModel.ordersSent.collectAsState()

    when (ordersSent) {

        is NetworkResponseState.Loading -> {
            Text("Cargando...")
        }

        is NetworkResponseState.Success -> {
            val order = (ordersSent as NetworkResponseState.Success<List<Order>>).result
            LazyVerticalGrid(columns = androidx.compose.foundation.lazy.grid.GridCells.Fixed(2)) {
                items(order.size) { index ->
                    Text(order[index].number)
                }
            }
        }

        is NetworkResponseState.Error -> {
            val error = (ordersSent as NetworkResponseState.Error).exception
            Text("Error: ${error.message}")
        }
    }


}

