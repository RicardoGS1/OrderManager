package com.virtualworld.mipymeanabelmaster.domain.mapper

import com.virtualworld.mipymeanabelmaster.core.dto.Order
import com.virtualworld.mipymeanabelmaster.core.dto.OrderProducts
import com.virtualworld.mipymeanabelmaster.domain.models.OrderDetail
import com.virtualworld.mipymeanabelmaster.screen.utils.convertMillisToDate

fun Order.toOrderDetail(): OrderDetail {

    val orderDetail = OrderDetail(
        number = this.number,
        state = this.state,
        dateOrder = convertMillisToDate( this.dateOrder.toLong()),
        dateDelivery = convertMillisToDate(this.dateDelivery.toLong()),
        importTotalUSD =calculateTotalImport(this.listOrderProducts).toString() ,
        importTotalMN = calculateTotalImportMN(this.listOrderProducts).toString(),
        unitTotal = calculateTotalUnit(this.listOrderProducts).toString(),
        listOrderProducts = this.listOrderProducts
    )

    return orderDetail

}


fun calculateTotalUnit(listOrderProducts: List<OrderProducts>): Float {

    var totalUsd = 0.0f

    listOrderProducts.forEach { product ->
        totalUsd += product.unit.toInt()
    }
    return totalUsd
}

fun calculateTotalImport(listOrderProducts: List<OrderProducts>): Float {

    var totalUsd = 0.0f

    listOrderProducts.forEach { product ->
        val priceUsd = product.priceUsd.toFloatOrNull() ?: 0.0f
        totalUsd += priceUsd * product.unit.toInt()
    }
    return totalUsd
}

fun calculateTotalImportMN(listOrderProducts: List<OrderProducts>): Float {

    var totalUsd = 0.0f

    listOrderProducts.forEach { product ->
        val priceUsd = product.priceMn.toFloatOrNull() ?: 0.0f
        totalUsd += priceUsd * product.unit.toInt()
    }
    return totalUsd
}