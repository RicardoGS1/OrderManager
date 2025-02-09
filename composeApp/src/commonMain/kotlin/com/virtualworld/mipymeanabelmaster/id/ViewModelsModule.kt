package com.virtualworld.mipymeanabelmaster.id

import com.virtualworld.mipymeanabelmaster.screen.orders.OrdersViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelsModule = module {

    viewModelOf(::OrdersViewModel)

}