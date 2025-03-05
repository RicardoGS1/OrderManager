package com.virtualworld.mipymeanabelmaster.core.repository

import com.virtualworld.mipymeanabelmaster.core.data.FirestoreDataSourceImp
import com.virtualworld.mipymeanabelmaster.core.model.NetworkResponseState
import com.virtualworld.mipymeanabelmaster.domain.mapper.toOrderDetail
import com.virtualworld.mipymeanabelmaster.domain.models.OrderDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OrderRepository(private val firestoreDataSourceImp: FirestoreDataSourceImp) {

    fun getOrderByCode(code: String, uid: String): Flow<NetworkResponseState<OrderDetail>> {


        return firestoreDataSourceImp.getOrderByCode(code,uid).map { order ->

            when (order) {
                is NetworkResponseState.Error -> {
                    NetworkResponseState.Error(order.exception)
                }

                is NetworkResponseState.Success -> {
                    NetworkResponseState.Success(order.result.toOrderDetail())
                }

                NetworkResponseState.Loading -> {
                    NetworkResponseState.Loading
                }

            }
        }

    }

    fun updateOrderState(newState: String, number: String, uid: String): Flow<NetworkResponseState<Boolean>> {


       return firestoreDataSourceImp.updateOrderState(newState,number,uid)

    }



}