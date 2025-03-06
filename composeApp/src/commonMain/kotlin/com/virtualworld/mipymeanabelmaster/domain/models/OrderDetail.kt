package com.virtualworld.mipymeanabelmaster.domain.models

import com.virtualworld.mipymeanabelmaster.core.dto.OrderProducts
import dev.gitlive.firebase.auth.ActionCodeResult

data class OrderDetail(

    val email: String ="",
    val number : String = "",
    val state : String = "",
    val dateOrder: String = "",
    val dateDelivery: String = "",
    val importTotalUSD: String = "",
    val importTotalMN: String = "",
    val unitTotal: String = "",
    val listOrderProducts: List<OrderProducts> = emptyList(),

    )



