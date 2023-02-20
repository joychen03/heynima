package itb.dam.jiafuchen.heynima.domain.usecases

import itb.dam.jiafuchen.heynima.data.source.database.relations.GameWithDeals
import itb.dam.jiafuchen.heynima.domain.models.Game
import itb.dam.jiafuchen.heynima.domain.repositories.GameRepository
import javax.inject.Inject
import itb.dam.jiafuchen.heynima.data.Result as Result

class GetGameUseCase @Inject constructor(
    private val repository : GameRepository
) {

    suspend operator fun invoke(id : String) : Result<Game>{
        return repository.getGame(id)
    }

}