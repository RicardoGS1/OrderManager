package com.virtualworld.mipymeanabelmaster.screen.orders

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.virtualworld.mipymeanabelmaster.core.dto.Order
import com.virtualworld.mipymeanabelmaster.core.model.NetworkResponseState


@Composable
fun OrdersScreen(viewModel: OrdersViewModel, onOrderClicked: (String, String) -> Unit) {

    val ordersSent by viewModel.ordersSent.collectAsState()


    when (ordersSent) {

        is NetworkResponseState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is NetworkResponseState.Success -> {
            val orders = (ordersSent as NetworkResponseState.Success<List<Order>>).result
            ColumnOrder(orders, onOrderClicked)
        }

        is NetworkResponseState.Error -> {
            val error = (ordersSent as NetworkResponseState.Error).exception
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error:  ${error.message}")
            }
        }
    }


}

@Composable
fun ColumnOrder(listOrders: List<Order>, onOrderClicked: (String, String) -> Unit) {

    var widthColum by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    Column(Modifier.fillMaxSize().onGloballyPositioned { coordinates ->
        widthColum = with(density) { coordinates.size.width.toDp() / 5 }
    }) {

        InfoColum(widthColum)
        ListOrders(listOrders, widthColum, onOrderClicked)
    }

}

@Composable
private fun ListOrders(
    listOrders: List<Order>,
    widthColum: Dp,
    onOrderClicked: (String, String) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(1.dp),
        modifier = Modifier.fillMaxSize().padding(4.dp)
    ) {

        items(listOrders, key = { it.number }) {
            ProductItem(order = it, widthColum, onOrderClicked)
        }
    }
}

@Composable
fun InfoColum(widthColum: Dp) {
    Box(Modifier.fillMaxWidth().padding(horizontal = 8.dp)) {

        Row(modifier = Modifier.fillMaxWidth()) {

            Text(
                modifier = Modifier.width(widthColum+40.dp),
                text = "Correo",
                maxLines = 1,
                fontWeight = FontWeight.Light,
            )

            Text(
                modifier = Modifier.width(widthColum / 1.5f),
                text = "Codigo",
                maxLines = 1,
                fontWeight = FontWeight.Light,
            )

            Text(
                modifier = Modifier.width(widthColum / 1.5f),
                text = "Estado",
                maxLines = 1,
                fontWeight = FontWeight.Light,
            )

            Text(
                modifier = Modifier.width(widthColum-10.dp),
                text = "Emitida",
                maxLines = 1,
                fontWeight = FontWeight.Light,
            )

            Text(
                modifier = Modifier.width(widthColum-10.dp),
                text = "Entrega",
                maxLines = 1,
                fontWeight = FontWeight.Light,
            )

            Text(
                modifier = Modifier.width(widthColum-20.dp),
                text = "Editar",
                maxLines = 1,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center

            )
        }
    }
}


@Composable
fun ProductItem(order: Order, widthColum: Dp, onOrderClicked: (String, String) -> Unit) {

    Box() {

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSecondary),
            elevation = CardDefaults.cardElevation(0.dp),
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
            ),
            shape = RectangleShape

        )
        {

            Row(
                modifier = Modifier.padding(6.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    modifier = Modifier.width(widthColum+40.dp),
                    text = order.email,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
                )

                Text(
                    modifier = Modifier.width(widthColum / 1.5f),
                    text = order.number,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
                )

                Text(
                    modifier = Modifier.width(widthColum / 1.5f),
                    text = order.state,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
                )

                Text(
                    modifier = Modifier.width(widthColum-10.dp),
                    text = order.dateOrder,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
                )

                Text(
                    modifier = Modifier.width(widthColum-10.dp),
                    text = order.dateDelivery,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
                )

                TextButton(
                    onClick = { onOrderClicked(order.number, order.uid) },
                    modifier = Modifier.width(widthColum-20.dp),

                    ) {
                    Text("Editar")
                }
            }
        }
    }

}




