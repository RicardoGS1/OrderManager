package com.virtualworld.mipymeanabelmaster.domain

import androidx.compose.animation.core.copy
import com.virtualworld.mipymeanabelmaster.core.model.NetworkResponseState
import com.virtualworld.mipymeanabelmaster.core.dto.Order
import com.virtualworld.mipymeanabelmaster.core.dto.User
import com.virtualworld.mipymeanabelmaster.core.dto.Users
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow

class GetOrdersSentUseCase() {

    val firestore: FirebaseFirestore = Firebase.firestore

    fun getOrdersSent(uid: String): Flow<NetworkResponseState<List<Order>>> = flow {

        println(uid)

        try {

            println("mmmm")
            firestore.collection("orders")
                .document(uid)
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


    fun getUsersUid(): Flow<NetworkResponseState<List<String>>> = flow {


        try {

            firestore.collection("orders")
                .snapshots.collect {

                    val listuid = it.documents.map { documentSnapshot ->
                        documentSnapshot.id

                    }

                    emit(NetworkResponseState.Success(listuid))
                }

        } catch (e: Exception) {
            NetworkResponseState.Error(e)
        }

    }


}