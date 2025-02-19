package com.virtualworld.mipymeanabelmaster.screen.details

import androidx.lifecycle.ViewModel
import com.virtualworld.mipymeanabelmaster.domain.models.OrderDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetailsViewModel ( private val orderId: String,) : ViewModel() {

    private val _orderState = MutableStateFlow<OrderDetail>(OrderDetail())
    val orderState: StateFlow<OrderDetail> get() = _orderState.asStateFlow()

    init {
        getOrderById()
    }


    private fun getOrderById(){


        _orderState.value = OrderDetail().copy(number = orderId, state = "firjigjrigjirjg")

    }


}