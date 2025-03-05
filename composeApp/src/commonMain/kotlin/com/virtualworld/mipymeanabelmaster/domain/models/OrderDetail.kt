package com.virtualworld.mipymeanabelmaster.domain.models

import com.virtualworld.mipymeanabelmaster.core.dto.OrderProducts

data class OrderDetail(

    val number : String = "",
    val state : String = "",
    val dateOrder: String = "",
    val dateDelivery: String = "",
    val importTotalUSD: String = "",
    val importTotalMN: String = "",
    val unitTotal: String = "",
    val listOrderProducts: List<OrderProducts> = emptyList(),

    )



