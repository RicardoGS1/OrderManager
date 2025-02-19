package com.virtualworld.mipymeanabelmaster.domain.mapper

import com.virtualworld.mipymeanabelmaster.core.dto.Order
import com.virtualworld.mipymeanabelmaster.domain.models.OrderDetail

fun Order.toOrderDetail(): OrderDetail {

    val orderDetail = OrderDetail(
        number = this.number,
        state = this.state,
        dateOrder = this.dateOrder,
        dateDelivery = this.dateDelivery,
        importTotalUSD = "",
        importTotalMN = "",
        unitTotal = "",
        listOrderProducts = this.listOrderProducts
    )

    return orderDetail

}