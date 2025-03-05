package com.virtualworld.mipymeanabelmaster.core.data

import com.virtualworld.mipymeanabelmaster.core.NotificationSender
import com.virtualworld.mipymeanabelmaster.core.dto.Order
import com.virtualworld.mipymeanabelmaster.core.model.NetworkResponseState
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.Source
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FirestoreDataSourceImp(private val notificationSender: NotificationSender) {

    val firestore: FirebaseFirestore = Firebase.firestore

    fun getOrderByCode(code: String, uid: String): Flow<NetworkResponseState<Order>> {

        return flow {

            try {

                firestore.collection("orders")
                    .document(uid)
                    .collection("collectionOrders")
                    .document(code)
                    .snapshots.collect {

                        val orders = it.data<Order>().copy(uid = uid)

                        emit(NetworkResponseState.Success(orders))

                    }
            } catch (e: Exception) {
                NetworkResponseState.Error(e)
            }


        }

    }


    fun updateOrderState(
        newState: String,
        code: String,
        uid: String
    ): Flow<NetworkResponseState<Boolean>> {






        return flow {

            try {

                var token: String? = null

               val documentSnapshot = firestore.collection("orders")
                    .document(uid).get()

                if (documentSnapshot.exists) {
                     token = documentSnapshot.get("token") as? String
                }


                firestore.collection("orders")
                    .document(uid)
                    .collection("collectionOrders")
                    .document(code)
                    .update(mapOf("state" to newState))


                if(token != null) {
                    notificationSender.sendNotification(
                        token = token!!,
                        title = "en estado de su orden $code a cambiado",
                        body = newState
                    )

                    println(token+newState)
                }



                emit(NetworkResponseState.Success(true))


            } catch (e: Exception) {
                NetworkResponseState.Error(e)
            }


        }


    }


}