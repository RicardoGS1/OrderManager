package com.virtualworld.mipymeanabelmaster.screen.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.virtualworld.mipymeanabelmaster.core.NetworkResponseState
import com.virtualworld.mipymeanabelmaster.core.Product
import com.virtualworld.mipymeanabelmaster.core.dto.Order
import com.virtualworld.mipymeanabelmaster.domain.GetOrdersSentUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OrdersViewModel(private val getOrdersSent: GetOrdersSentUseCase) : ViewModel() {

    private val _ordersSent = MutableStateFlow<NetworkResponseState<List<Order>>>(NetworkResponseState.Loading)
    val ordersSent: StateFlow<NetworkResponseState<List<Order>>> = _ordersSent

    init {
        getOrdersSent()
    }

    private fun getOrdersSent() {
        viewModelScope.launch {
            getOrdersSent.getOrdersSent().collect{ orders->

                _ordersSent.update { orders }

            }
        }


    }


}