package itb.dam.jiafuchen.heynima.domain.repositories

import itb.dam.jiafuchen.heynima.data.source.database.relations.GameWithDeals
import itb.dam.jiafuchen.heynima.domain.models.Game
import itb.dam.jiafuchen.heynima.domain.models.Games
import itb.dam.jiafuchen.heynima.domain.models.Store
import kotlinx.coroutines.flow.Flow
import itb.dam.jiafuchen.heynima.data.Result as Result

interface GameRepository {

    fun getGames(title : String) : Flow<Result<List<Games>>>

    suspend fun getGame(id : String) : Result<Game>

    fun getStores() : Flow<Result<List<Store>>>

    suspend fun getFavGamesFromDatabase() : List<Games>

    suspend fun getStoresFromDatabase() : List<Store>

    suspend fun getFavGamesWithDealsAndStoresWithIDFromDB(gameID : String) : GameWithDeals?

    suspend fun deleteAllStores()

    suspend fun deleteAllDeals()

    suspend fun deleteAllGames()
}