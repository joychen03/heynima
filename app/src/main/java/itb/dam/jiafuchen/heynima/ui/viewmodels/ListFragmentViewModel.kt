package itb.dam.jiafuchen.heynima.ui.viewmodels

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import itb.dam.jiafuchen.heynima.domain.models.Games
import itb.dam.jiafuchen.heynima.domain.usecases.GetGamesUseCase
import itb.dam.jiafuchen.heynima.data.Result
import itb.dam.jiafuchen.heynima.data.source.database.dao.GameDao
import itb.dam.jiafuchen.heynima.data.source.database.entity.toDomain
import itb.dam.jiafuchen.heynima.data.source.database.relations.toDomain
import itb.dam.jiafuchen.heynima.data.source.remote.dto.toDatabase
import itb.dam.jiafuchen.heynima.domain.models.toDatabase
import itb.dam.jiafuchen.heynima.domain.usecases.GetGameUseCase
import itb.dam.jiafuchen.heynima.domain.usecases.GetStoresUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListFragmentViewModel @Inject constructor(
    private val getGamesUseCase: GetGamesUseCase,
    private val getGameUseCase: GetGameUseCase,
    private val dao : GameDao
) : ViewModel() {

    var favGames : List<Games> = emptyList()

    private val _games = MutableLiveData<List<Games>>()
    val games : LiveData<List<Games>> = _games

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage : LiveData<String> = _errorMessage

    fun getGames(gameTitle : String){
        viewModelScope.launch {
            _isLoading.postValue(true)

            favGames = dao.getAllGames().map { it.toDomain() }

            getGamesUseCase(gameTitle).onEach { result ->

                when(result){
                    is Result.Success ->{
                        _isLoading.postValue(false)
                        _games.postValue(result.data!!)

                        if(result.data.isEmpty()){
                            _errorMessage.postValue("Game \"${gameTitle}\" not found")
                        }
                    }
                    is Result.Error ->{
                        _isLoading.postValue(false)
                        _errorMessage.postValue(result.message ?: "Unknown Error")
                    }
                    else -> {}
                }

            }.launchIn(this)

        }
    }

    fun liked(games: Games) {
        viewModelScope.launch {
            val result = getGameUseCase(games.id)

            when(result){
                is Result.Success ->{
                    dao.insertGames(result.data!!.toDatabase())
                    dao.insertDeals(result.data.deals.map { it.toDatabase() })
                }
                is Result.Error ->{
                    _errorMessage.postValue(result.message!!)
                }
                else -> {}
            }
        }
    }

    fun likeRemoved(games: Games) {
        viewModelScope.launch {
            dao.removeGame(games.id)
        }
    }
}
