package com.virtualworld.mipymeanabelmaster.screen.details

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.virtualworld.mipymeanabelmaster.core.dto.OrderProducts

@Composable
fun DetailsScreen(viewModel: DetailsViewModel) {

    val orderState by viewModel.orderState.collectAsStateWithLifecycle()
    val onOrderStateChanged = { state: String -> viewModel.updateOrderState(state) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
        // .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Order Number: ${orderState.number}",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            StateSelector(
                currentStatus = orderState.state,
                onOrderStateChanged = onOrderStateChanged
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Order Date: ${orderState.dateOrder}",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

            Text(
                text = "Total Import (USD): ${orderState.importTotalUSD}",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Delivery Date: ${orderState.dateDelivery}",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Total Import (MN): ${orderState.importTotalMN}",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Total Units: ${orderState.unitTotal}",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Products:",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(orderState.listOrderProducts) { product ->
                ProductItem(product = product)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StateSelector(currentStatus: String, onOrderStateChanged: (String) -> Unit) {

    val options = listOf("Enviada", "Procesando", "Lista", "Cancelada")
    var expanded by remember { mutableStateOf(false) }


    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
            .clickable { expanded = !expanded }
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .width(100.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = currentStatus,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.Filled.ArrowDropDown,
            contentDescription = "Dropdown",
            modifier = Modifier.size(24.dp)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        expanded = false
                        onOrderStateChanged(selectionOption)
                    }
                )
            }
        }
    }
}


@Composable
fun ProductItem(product: OrderProducts) {


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        //elevation = 4.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = product.image,
                contentDescription = product.name,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Price (MN): ${product.priceMn}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Price (USD): ${product.priceUsd}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Unit: ${product.unit}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}