package itb.dam.jiafuchen.heynima.data.source.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import itb.dam.jiafuchen.heynima.domain.models.Game
import itb.dam.jiafuchen.heynima.domain.models.Games

@Entity(tableName = "game_table")
data class GameEntity(
    @PrimaryKey
    @ColumnInfo(name = "game_id") val gameID : String,
    @ColumnInfo(name = "name") val name : String,
    @ColumnInfo(name = "image") val image : String,
    @ColumnInfo(name = "cheapest_date") val cheapestDate: Long,
    @ColumnInfo(name = "cheapest_price") val cheapestPrice: String
)


fun GameEntity.toDomain() = Games(id = gameID, name = name, image = image, cheapestPrice = cheapestPrice, cheapestDealID = "")