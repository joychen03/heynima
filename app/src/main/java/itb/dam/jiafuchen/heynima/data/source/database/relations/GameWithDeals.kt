package itb.dam.jiafuchen.heynima.data.source.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import itb.dam.jiafuchen.heynima.data.source.database.entity.DealEntity
import itb.dam.jiafuchen.heynima.data.source.database.entity.GameEntity
import itb.dam.jiafuchen.heynima.data.source.remote.dto.CheapestPriceEver
import itb.dam.jiafuchen.heynima.domain.models.Game

data class GameWithDeals(
    @Embedded val game: GameEntity,
    @Relation(
        entity = DealEntity::class,
        parentColumn = "game_id",
        entityColumn = "game_id"
    )
    val deals : List<DealWithStore>
)


fun GameWithDeals.toDomain() = Game(
    image = game.image,
    name = game.name,
    id = game.gameID,
    cheapestPriceEver = CheapestPriceEver(game.cheapestDate, game.cheapestPrice),
    deals = deals.map {
        it.toDomain()
    }
)