package itb.dam.jiafuchen.heynima.domain.models

import itb.dam.jiafuchen.heynima.data.source.database.entity.StoreEntity

data class Store(
    val id : String,
    val name : String,
    val image : String,
)

fun Store.toDatabase() = StoreEntity(storeID =  id, name = name, image = image)