package com.virtualworld.mipymeanabelmaster.domain.usecase

import com.virtualworld.mipymeanabelmaster.core.model.NetworkResponseState
import com.virtualworld.mipymeanabelmaster.core.repository.OrderRepository
import com.virtualworld.mipymeanabelmaster.domain.models.OrderDetail
import kotlinx.coroutines.flow.Flow

class GetOrderByCodeUseCase(private val orderRepository: OrderRepository) {

    fun getOrderByCode(code:String,uid:String): Flow<NetworkResponseState<OrderDetail>> {

        return orderRepository.getOrderByCode(code,uid)

    }



}