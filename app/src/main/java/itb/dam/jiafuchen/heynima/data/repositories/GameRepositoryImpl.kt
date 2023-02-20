package itb.dam.jiafuchen.heynima.data.repositories

import itb.dam.jiafuchen.heynima.data.Result
import itb.dam.jiafuchen.heynima.data.source.database.dao.GameDao
import itb.dam.jiafuchen.heynima.data.source.database.entity.toDomain
import itb.dam.jiafuchen.heynima.data.source.database.relations.GameWithDeals
import itb.dam.jiafuchen.heynima.data.source.remote.CheapSharkApi
import itb.dam.jiafuchen.heynima.data.source.remote.dto.toGame
import itb.dam.jiafuchen.heynima.data.source.remote.dto.toListGames
import itb.dam.jiafuchen.heynima.data.source.remote.dto.toListStores
import itb.dam.jiafuchen.heynima.domain.models.*
import itb.dam.jiafuchen.heynima.domain.repositories.GameRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val api : CheapSharkApi,
    private val db : GameDao
) : GameRepository{

    override fun getGames(title: String): Flow<Result<List<Games>>> = flow {
        emit(Result.Loading())
        try {
            val response = api.getGames(title).toListGames()
            emit(Result.Success(response))
        }catch (e : HttpException){
            emit(Result.Error(
                message = e.message,
                data = null
            ))
        }catch (e : IOException){
            emit(Result.Error(
                message = "Couldn't reach server, check your internet connection",
                data = null
            ))
        }
    }

    override suspend fun getGame(id: String): Result<Game> {
        val response = try {
            api.getGame(id)
        }catch (e : Exception){
            return Result.Error("An unknown error occurred")
        }
        response.gameID = id
        return Result.Success(response.toGame())
    }

    override fun getStores(): Flow<Result<List<Store>>> = flow {
        emit(Result.Loading())

        try {
            val response = api.getStores().toListStores()
            db.insertStores(response.map { it.toDatabase() })
            emit(Result.Success(response))
        }catch (e : HttpException){
            emit(Result.Error(
                message = "Oops, something went wrong",
                data = null
            ))
        }catch (e : IOException){
            emit(Result.Error(
                message = "Couldn't reach server, check your internet connection",
                data = null
            ))
        }
    }

    override suspend fun getFavGamesFromDatabase(): List<Games> {
        return db.getAllGames().map { it.toDomain() }
    }

    override suspend fun getStoresFromDatabase(): List<Store> {
        return db.getAllStores().map { it.toDomain() }
    }

    override suspend fun getFavGamesWithDealsAndStoresWithIDFromDB(gameID: String): GameWithDeals? {
        return db.getGameWithDealsAndStoresWithID(gameID)
    }

    override suspend fun deleteAllStores() {
        db.deleteAllStores()
    }

    override suspend fun deleteAllDeals() {
        db.deleteAllDeals()
    }

    override suspend fun deleteAllGames() {
        db.deleteAllGames()
    }
}