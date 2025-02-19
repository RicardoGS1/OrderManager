package com.virtualworld.mipymeanabelmaster.screen.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.virtualworld.mipymeanabelmaster.core.model.NetworkResponseState
import com.virtualworld.mipymeanabelmaster.domain.models.OrderDetail
import com.virtualworld.mipymeanabelmaster.domain.usecase.GetOrderByCodeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val orderId: String,
    private val uid: String,
    private val getOrderByCodeUseCase: GetOrderByCodeUseCase
) : ViewModel() {

    private val _orderState = MutableStateFlow<OrderDetail>(OrderDetail())
    val orderState: StateFlow<OrderDetail> get() = _orderState.asStateFlow()

    init {
        getOrderById()
    }


    private fun getOrderById() {

        viewModelScope.launch {
            getOrderByCodeUseCase.getOrderByCode(orderId, uid).collect {


                when (it) {
                    is NetworkResponseState.Error -> TODO()
                    NetworkResponseState.Loading -> TODO()
                    is NetworkResponseState.Success -> {
                        _orderState.value = it.result
                    }
                }

            }
        }


    }


}