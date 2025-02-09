package com.virtualworld.mipymeanabelmaster.domain

import com.virtualworld.mipymeanabelmaster.core.NetworkResponseState
import com.virtualworld.mipymeanabelmaster.core.Product
import com.virtualworld.mipymeanabelmaster.core.dto.Order
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetOrdersSentUseCase {

    fun getOrdersSent(): Flow<NetworkResponseState<List<Order>>> = flow {

        val firestore: FirebaseFirestore = Firebase.firestore

        try {

                firestore.collection("orders")
                    .document("2qhrKw3wDoT0mx4eiZBh69KNf3j1")
                    .collection("collectionOrders")
                    .snapshots.collect {

                        val orders = it.documents.map { documentSnapshot ->
                            documentSnapshot.data<Order>()
                                .copy(listOrderProducts = emptyList()) // hay que separar la lista de de la orden proximamente
                        }
                        emit(NetworkResponseState.Success(orders))
                    }

        } catch (e: Exception) {
            NetworkResponseState.Error(e)
        }
    }


    fun getOrdersSjent(): Flow<NetworkResponseState<List<Product>>> = flow {

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