package com.virtualworld.mipymeanabelmaster.screen.orders

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.virtualworld.mipymeanabelmaster.core.dto.Order
import com.virtualworld.mipymeanabelmaster.core.model.NetworkResponseState


@Composable
fun OrdersScreen(viewModel: OrdersViewModel) {

    val ordersSent by viewModel.ordersSent.collectAsState()


    val listState = rememberLazyGridState()

    when (ordersSent) {

        is NetworkResponseState.Loading -> {
            Text("Cargando...")
        }

        is NetworkResponseState.Success -> {
            val orders = (ordersSent as NetworkResponseState.Success<List<Order>>).result
            GridOrders(orders, listState)
        }

        is NetworkResponseState.Error -> {
            val error = (ordersSent as NetworkResponseState.Error).exception
            Text("Error: ${error.message}")
        }
    }


}

@Composable
fun GridOrders(listOrders: List<Order>, listState: LazyGridState) {

    val maxWidthColumn = 500.dp
    var widthColumn by rememberSaveable { (mutableStateOf(maxWidthColumn)) }
    val widthColumnUpdate = { width: Dp -> widthColumn = width }

    Box(Modifier.fillMaxSize()) {

        LazyVerticalGrid(
            state = listState,
            columns = GridCells.Adaptive(widthColumn),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier.padding(horizontal = 8.dp)

        ) {

            items(listOrders, key = { it.number }) {
                ProductItem(order = it, widthColumnUpdate, widthColumn)
            }
        }
    }

}

@Composable
fun ProductItem(order: Order, widthColumnUpdate: (Dp) -> Unit, widthColumn: Dp) {

    var boxWidthDp by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current.density


    Box() {

        Card(
            modifier = Modifier.onGloballyPositioned { coordinates ->
                boxWidthDp = (coordinates.size.width / density).dp
            },
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSecondary),
            elevation = CardDefaults.cardElevation(0.dp),
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
            ),

            )
        {

            Column(modifier = Modifier.padding(4.dp)) {

                Text(
                    text = order.email,
                    maxLines = 1,
                    //textAlign = TextAlign.End,
                    modifier = Modifier.padding(horizontal = 4.dp),
                    // color = Color.Red.copy(alpha = 0.8f),
                    fontWeight = FontWeight.Bold,
                )

                Row(horizontalArrangement = Arrangement.SpaceBetween) {

                    Text(
                        text = order.number,
                        maxLines = 1,
                        //textAlign = TextAlign.End,
                        modifier = Modifier.padding(horizontal = 4.dp),
                        // color = Color.Red.copy(alpha = 0.8f),
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = order.state,
                        maxLines = 1,
                        //textAlign = TextAlign.End,
                        modifier = Modifier.padding(horizontal = 4.dp),
                        // color = Color.Red.copy(alpha = 0.8f),
                        fontWeight = FontWeight.Bold,
                    )

                }

                Row(horizontalArrangement = Arrangement.SpaceBetween) {

                    Text(
                        text = order.dateOrder,
                        maxLines = 1,
                        // textAlign = TextAlign.End,
                        modifier = Modifier.padding(horizontal = 4.dp),
                        //color = Color.Red.copy(alpha = 0.8f),
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = order.dateDelivery,
                        maxLines = 1,
                        //textAlign = TextAlign.End,
                        modifier = Modifier.padding(horizontal = 4.dp),
                        //color = Color.Red.copy(alpha = 0.8f),
                        fontWeight = FontWeight.Bold,
                    )
                }
            }

        }

    }

    if (boxWidthDp != 0.dp && widthColumn == 500.dp) {
        widthColumnUpdate(boxWidthDp)
    }
}


