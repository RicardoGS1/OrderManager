package com.virtualworld.mipymeanabelmaster.core.data

import com.virtualworld.mipymeanabelmaster.core.dto.Order
import com.virtualworld.mipymeanabelmaster.core.model.NetworkResponseState
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FirestoreDataSourceImp() {

    val firestore: FirebaseFirestore = Firebase.firestore

    fun getOrderByCode(code: String, uid: String): Flow<NetworkResponseState<Order>> {

        return flow {

            try {

                firestore.collection("orders")
                    .document(uid)
                    .collection("collectionOrders")
                    .document(code)
                    .snapshots.collect {

                        val orders = it.data<Order>().copy(uid=uid)

                        emit(NetworkResponseState.Success(orders))

                    }
            } catch (e: Exception) {
                NetworkResponseState.Error(e)
            }


        }

    }


}