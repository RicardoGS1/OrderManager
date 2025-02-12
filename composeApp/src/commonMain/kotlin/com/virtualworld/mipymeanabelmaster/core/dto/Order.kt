package com.virtualworld.mipymeanabelmaster.core.dto

import kotlinx.serialization.Serializable

@Serializable
data class Users(

    val users : List<User> = listOf()
)


@Serializable
data class User(
    val uid :String = "",
    val collectionOrders : List<Order> = listOf()
)


@Serializable
data class Order(

    val name: String = "",
    val email: String = "",
    val number : String = "",
    val state : String = "",
    val dateOrder: String = "",
    val dateDelivery: String = "",
    val listOrderProducts: List<OrderProducts> = emptyList(),

)

@Serializable
data class OrderProducts(

    val idp: String = "",
    val name: String = "",
    val priceMn: String = "",
    val priceUsd: String = "",
    val image:String = "",
    val unit: String = ""

)