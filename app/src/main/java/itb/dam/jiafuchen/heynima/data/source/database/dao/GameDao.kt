package itb.dam.jiafuchen.heynima.data.source.database.dao

import androidx.room.*
import itb.dam.jiafuchen.heynima.data.source.database.entity.DealEntity
import itb.dam.jiafuchen.heynima.data.source.database.entity.GameEntity
import itb.dam.jiafuchen.heynima.data.source.database.entity.StoreEntity
import itb.dam.jiafuchen.heynima.data.source.database.relations.GameWithDeals
import itb.dam.jiafuchen.heynima.domain.models.Game

@Dao
interface GameDao {

    @Transaction
    @Query("SELECT * FROM game_table")
    suspend fun getAllGamesWithDealsAndStores() : List<GameWithDeals>

    @Transaction
    @Query("SELECT * FROM game_table WHERE game_id = :gameID")
    suspend fun getGameWithDealsAndStoresWithID(gameID : String) : GameWithDeals?

    @Query("SELECT * FROM game_table")
    suspend fun getAllGames() : List<GameEntity>

    @Query("SELECT * FROM deal_table")
    suspend fun getAllDeals() : List<DealEntity>

    @Query("SELECT * FROM store_table")
    suspend fun getAllStores() : List<StoreEntity>

    @Query("SELECT * FROM game_table WHERE game_id = :id")
    suspend fun getGamesWithID(id: String): GameEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGames(game : GameEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeals(deals : List<DealEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStores(stores : List<StoreEntity>)

    @Query("DELETE FROM store_table")
    suspend fun deleteAllStores()

    @Query("DELETE FROM deal_table")
    suspend fun deleteAllDeals()

    @Query("DELETE FROM game_table")
    suspend fun deleteAllGames()

    @Query("DELETE FROM game_table WHERE game_id = :id")
    suspend fun removeGame(id: String)


}