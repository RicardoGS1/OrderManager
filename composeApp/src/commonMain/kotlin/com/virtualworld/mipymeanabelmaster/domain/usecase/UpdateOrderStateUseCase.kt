package com.virtualworld.mipymeanabelmaster.domain.usecase

import com.virtualworld.mipymeanabelmaster.core.model.NetworkResponseState
import com.virtualworld.mipymeanabelmaster.core.repository.OrderRepository
import kotlinx.coroutines.flow.Flow

class UpdateOrderStateUseCase(private val orderRepository: OrderRepository) {

    operator fun invoke(newState: String, number: String, uid: String): Flow<NetworkResponseState<Boolean>> {

        return orderRepository.updateOrderState(newState, number, uid)


    }

}