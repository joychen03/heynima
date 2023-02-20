package itb.dam.jiafuchen.heynima.data.source.remote.dto

import itb.dam.jiafuchen.heynima.domain.models.Game

data class GameDto(
    var gameID : String = "",
    val info: Info,
    val cheapestPriceEver: CheapestPriceEver,
    val deals: List<Deal>
)

fun GameDto.toGame() : Game{
    return Game(
        id = gameID,
        name = info.title,
        image = info.thumb,
        cheapestPriceEver = cheapestPriceEver,
        deals = deals.map {
            Deal(
                gameID = gameID,
                dealID = it.dealID,
                price = it.price,
                storeID = it.storeID,
                retailPrice = it.retailPrice,
                savings = it.savings
            )
        }
    )


}