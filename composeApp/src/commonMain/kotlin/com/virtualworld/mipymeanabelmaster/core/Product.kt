package com.virtualworld.mipymeanabelmaster.core

import kotlinx.serialization.Serializable


@Serializable
data class Product (
    val idp: String,
    val name: String,
    val priceMn: String,
    val priceUsd: String,
    val detail:String,
    val available:String,
    val image:String,
    val category:String
)