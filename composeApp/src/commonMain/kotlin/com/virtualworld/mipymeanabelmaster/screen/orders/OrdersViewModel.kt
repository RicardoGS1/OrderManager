package com.virtualworld.mipymeanabelmaster.screen.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.virtualworld.mipymeanabelmaster.core.dto.Order
import com.virtualworld.mipymeanabelmaster.core.model.NetworkResponseState
import com.virtualworld.mipymeanabelmaster.domain.usecase.GetOrdersSentUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OrdersViewModel(private val getOrdersSent: GetOrdersSentUseCase) : ViewModel() {

    private val _ordersSent = MutableStateFlow<NetworkResponseState<List<Order>>>(
        NetworkResponseState.Loading
    )
    val ordersSent: StateFlow<NetworkResponseState<List<Order>>> get() = filteredProductsState()

    private val _searchText = MutableStateFlow<String>("")
    val searchText: StateFlow<String> get() = _searchText.asStateFlow()


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

                    flow {
                        emit(NetworkResponseState.Error(Exception("No orders found")))
                    }

                }
            }.collect { value ->
                _ordersSent.value = value
            }


        }

    }

    fun updateSearchText(searchText: String) {
        _searchText.update { searchText }
    }

    private fun filteredProductsState(): StateFlow<NetworkResponseState<List<Order>>> =
        combine(
            _searchText,
           // _selectedCategoryState,
            _ordersSent
        ) { text, listAllProducts ->


            when (listAllProducts) {
                is NetworkResponseState.Success -> {
                    val filteredProducts = listAllProducts.result.filter { product ->
                        product.number.contains(text, ignoreCase = true)
                    }
                    NetworkResponseState.Success(filteredProducts)
                }

                is NetworkResponseState.Error -> NetworkResponseState.Error(listAllProducts.exception)
                is NetworkResponseState.Loading -> NetworkResponseState.Loading
            }

        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            _ordersSent.value
        )





}
