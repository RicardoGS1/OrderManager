package com.virtualworld.mipymeanabelmaster.screen.orders

import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.virtualworld.mipymeanabelmaster.core.NetworkResponseState
import com.virtualworld.mipymeanabelmaster.core.Product
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Composable
fun OrdersScreen() {


    Text("nnjnnnnnnmnmnmnmnmnnnmnmnmnmn")

    val productRepository = jjjjj()
    val productsFlow = productRepository.getAllProducts()

    val productsState: NetworkResponseState<List<Product>> by productsFlow.collectAsState(
        initial = NetworkResponseState.Loading
    )


    val  a =jjjjj().getAllProducts()

    when (productsState) {
        is NetworkResponseState.Loading -> {
            Text("Cargando...")
        }

        is NetworkResponseState.Success -> {
            val products = (productsState as NetworkResponseState.Success<List<Product>>).result
            LazyVerticalGrid(columns = androidx.compose.foundation.lazy.grid.GridCells.Fixed(2)) {
                items(products.size) { index ->
                    Text(products[index].name)
                }
            }
        }

        is NetworkResponseState.Error -> {
            val error = (productsState as NetworkResponseState.Error).exception
            Text("Error: ${error.message}")
        }
    }


}

class jjjjj() {
    fun getAllProducts(): Flow<NetworkResponseState<List<Product>>> = flow {

        val firestore: FirebaseFirestore = Firebase.firestore

        try {

            firestore.collection("PRODUCTS").snapshots.collect { querySnapshot ->

                val products = querySnapshot.documents.map { documentSnapshot ->
                    documentSnapshot.data<Product>()
                }
                if (products.isEmpty()) {
                    println("eeeeeee")
                    //throw ProductEmptyExceptionee()
                } else {
                    emit(NetworkResponseState.Success(products))
                }
            }

        } catch (e: Exception) {
            emit(NetworkResponseState.Error(e))
        }
    }


}