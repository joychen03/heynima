package itb.dam.jiafuchen.heynima.domain.models

import itb.dam.jiafuchen.heynima.data.source.database.entity.GameEntity
import itb.dam.jiafuchen.heynima.data.source.remote.dto.CheapestPriceEver
import itb.dam.jiafuchen.heynima.data.source.remote.dto.Deal

data class Game(
    var id : String = "",
    val name : String,
    val image : String,
    val cheapestPriceEver : CheapestPriceEver,
    val deals : List<Deal>
)

fun Game.toDatabase() = GameEntity(gameID = id, name = name, image = image, cheapestPrice = cheapestPriceEver.price, cheapestDate = cheapestPriceEver.date)