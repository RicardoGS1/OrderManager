package com.virtualworld.mipymeanabelmaster.screen.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.virtualworld.mipymeanabelmaster.core.model.NetworkResponseState
import com.virtualworld.mipymeanabelmaster.domain.models.OrderDetail
import com.virtualworld.mipymeanabelmaster.domain.usecase.GetOrderByCodeUseCase
import com.virtualworld.mipymeanabelmaster.domain.usecase.UpdateOrderStateUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val orderId: String,
    private val uid: String,
    private val getOrderByCodeUseCase: GetOrderByCodeUseCase,
    private val updateOrderStateUseCase: UpdateOrderStateUseCase
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

    fun updateOrderState(newState: String) {

        viewModelScope.launch {
            updateOrderStateUseCase(newState, _orderState.value.number, uid).collect {

                when (it) {
                    is NetworkResponseState.Error -> {}
                    NetworkResponseState.Loading -> {}
                    is NetworkResponseState.Success -> {}
                }
            }

        }

    }


}