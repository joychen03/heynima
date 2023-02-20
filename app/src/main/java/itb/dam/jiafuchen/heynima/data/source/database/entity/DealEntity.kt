package itb.dam.jiafuchen.heynima.data.source.database.entity

import androidx.room.*

@Entity(tableName = "deal_table", indices = [Index(value = ["deal_id"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = GameEntity::class,
            parentColumns = ["game_id"],
            childColumns = ["game_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class DealEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id : Int = 0,
    @ColumnInfo(name = "game_id") val gameID : String,
    @ColumnInfo(name = "deal_id") val dealID : String,
    @ColumnInfo(name = "price") val price: String,
    @ColumnInfo(name = "retail_price") val retailPrice: String,
    @ColumnInfo(name = "saving") val savings: String,
    @ColumnInfo(name = "store_id") val storeID: String
)
