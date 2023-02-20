package itb.dam.jiafuchen.heynima.data.source.remote

import itb.dam.jiafuchen.heynima.data.source.remote.dto.GameDto
import itb.dam.jiafuchen.heynima.data.source.remote.dto.GamesDto
import itb.dam.jiafuchen.heynima.data.source.remote.dto.StoreDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CheapSharkApi {

    @GET("games")
    suspend fun getGames(
        @Query("title") title : String
    ): GamesDto

    @GET("games")
    suspend fun getGame(
        @Query("id") id : String
    ) : GameDto

    @GET("stores")
    suspend fun getStores() : StoreDto
}