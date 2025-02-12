package com.virtualworld.mipymeanabelmaster.screen.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.virtualworld.mipymeanabelmaster.core.model.NetworkResponseState
import com.virtualworld.mipymeanabelmaster.core.dto.Order
import com.virtualworld.mipymeanabelmaster.domain.GetOrdersSentUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.lang.Exception

class OrdersViewModel(private val getOrdersSent: GetOrdersSentUseCase) : ViewModel() {

    private val _ordersSent = MutableStateFlow<NetworkResponseState<List<Order>>>(
        NetworkResponseState.Loading
    )
    val ordersSent: StateFlow<NetworkResponseState<List<Order>>> = _ordersSent


    init {
        getOrdersSent()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getOrdersSent() {

        viewModelScope.launch {

          getOrdersSent.getUsersUid().flatMapMerge { uids ->

                if (uids is NetworkResponseState.Success) {

                    val listFlows = uids.result.map { uid ->
                        getOrdersSent.getOrdersSent(uid)
                    }

                    if (listFlows.isNotEmpty()) {

                        combine(listFlows) { arrayOfOrders ->

                            val allOrders = mutableListOf<Order>()

                            arrayOfOrders.forEach { networkResponse ->
                                if (networkResponse is NetworkResponseState.Success) {

                                    allOrders.addAll(networkResponse.result)
                                }
                            }

                            NetworkResponseState.Success(allOrders.toList())
                        }
                    } else {
                        flowOf(NetworkResponseState.Error(Exception("No orders found")))
                    }
                } else {

                    flow{
                       emit( NetworkResponseState.Error(Exception("No orders found")) )
                    }

                }
            }.collect { value ->
                _ordersSent.value = value
            }


        }

    }

}
