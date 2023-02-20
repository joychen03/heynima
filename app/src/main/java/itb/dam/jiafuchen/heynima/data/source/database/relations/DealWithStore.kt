package itb.dam.jiafuchen.heynima.data.source.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import itb.dam.jiafuchen.heynima.data.source.database.entity.DealEntity
import itb.dam.jiafuchen.heynima.data.source.database.entity.GameEntity
import itb.dam.jiafuchen.heynima.data.source.database.entity.StoreEntity
import itb.dam.jiafuchen.heynima.data.source.remote.dto.Deal

data class DealWithStore(
    @Embedded val deal: DealEntity,
    @Relation(
        parentColumn = "store_id",
        entityColumn = "store_id"
    )
    val store : StoreEntity
)

fun DealWithStore.toDomain() = Deal(
    dealID = deal.dealID,
    gameID = deal.gameID,
    storeID = store.storeID,
    savings = deal.savings,
    price = deal.price,
    retailPrice = deal.retailPrice
)