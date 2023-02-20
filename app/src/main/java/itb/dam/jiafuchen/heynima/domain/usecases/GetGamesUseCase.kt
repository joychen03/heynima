package itb.dam.jiafuchen.heynima.domain.usecases

import itb.dam.jiafuchen.heynima.domain.models.Game
import itb.dam.jiafuchen.heynima.domain.models.Games
import itb.dam.jiafuchen.heynima.domain.repositories.GameRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import itb.dam.jiafuchen.heynima.data.Result as Result

class GetGamesUseCase @Inject constructor(
    private val repository: GameRepository
){
    operator fun invoke(title : String) : Flow<Result<List<Games>>>{
        return repository.getGames(title)
    }

}