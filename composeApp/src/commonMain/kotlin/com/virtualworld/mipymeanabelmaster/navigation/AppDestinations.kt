package com.virtualworld.mipymeanabelmaster.navigation


interface AppDestinations {
    val route: String
    val title: String
}


object RouteOrders : AppDestinations {
    override val route = "orders"
    override val title = "List Orders"
}

object RouteDetails : AppDestinations {
    override val route = "details"
    override val title = "Details"
}



