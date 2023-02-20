package itb.dam.jiafuchen.heynima.data.source.remote.dto

import itb.dam.jiafuchen.heynima.domain.models.Games

class GamesDto : ArrayList<GamesDtoItem>()

fun GamesDto.toListGames() : List<Games>{

    val resultEntries = this.mapIndexed { index, entries ->
        Games(
            id = entries.gameID,
            name = entries.external,
            image = entries.thumb,
            cheapestPrice = entries.cheapest,
            cheapestDealID = entries.cheapestDealID
        )
    }

    return resultEntries
}