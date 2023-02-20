package itb.dam.jiafuchen.heynima.data.source.remote.dto

import itb.dam.jiafuchen.heynima.data.source.database.entity.DealEntity

data class Deal(
    var gameID: String = "",
    val dealID: String,
    val price: String,
    val retailPrice: String,
    val savings: String,
    val storeID: String
)

fun Deal.toDatabase() = DealEntity(gameID = gameID, dealID = dealID, price = price, retailPrice = retailPrice, savings = savings, storeID = storeID)