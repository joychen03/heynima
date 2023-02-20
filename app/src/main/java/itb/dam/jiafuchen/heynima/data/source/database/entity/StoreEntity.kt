package itb.dam.jiafuchen.heynima.data.source.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import itb.dam.jiafuchen.heynima.domain.models.Store

@Entity(tableName = "store_table")
data class StoreEntity(
    @PrimaryKey
    @ColumnInfo(name = "store_id") val storeID : String,
    @ColumnInfo(name = "name") val name : String,
    @ColumnInfo(name = "image") val image : String
)


fun StoreEntity.toDomain() = Store(storeID, name, image)