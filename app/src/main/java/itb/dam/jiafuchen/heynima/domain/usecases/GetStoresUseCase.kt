package itb.dam.jiafuchen.heynima.domain.usecases

import itb.dam.jiafuchen.heynima.data.Result
import itb.dam.jiafuchen.heynima.domain.models.Store
import itb.dam.jiafuchen.heynima.domain.repositories.GameRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStoresUseCase @Inject constructor(
    private val repository : GameRepository
){
    operator fun invoke() : Flow<Result<List<Store>>> {
        return repository.getStores()
    }
}