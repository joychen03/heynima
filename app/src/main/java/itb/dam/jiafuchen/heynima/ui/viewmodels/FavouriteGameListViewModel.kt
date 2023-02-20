package itb.dam.jiafuchen.heynima.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import itb.dam.jiafuchen.heynima.data.Result
import itb.dam.jiafuchen.heynima.data.source.database.dao.GameDao
import itb.dam.jiafuchen.heynima.data.source.database.entity.toDomain
import itb.dam.jiafuchen.heynima.data.source.database.relations.toDomain
import itb.dam.jiafuchen.heynima.data.source.remote.dto.toDatabase
import itb.dam.jiafuchen.heynima.domain.models.Games
import itb.dam.jiafuchen.heynima.domain.models.toDatabase
import itb.dam.jiafuchen.heynima.domain.usecases.GetGameUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteGameListViewModel @Inject constructor(
    private val getGameUseCase: GetGameUseCase,
    private val dao : GameDao
) : ViewModel() {
    private val _games = MutableLiveData<List<Games>>()
    val games : LiveData<List<Games>> = _games

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun getGames(){
        viewModelScope.launch {
            _isLoading.postValue(true)

            val games = dao.getAllGames().map { it.toDomain() }
            _games.postValue(games)

            _isLoading.postValue(false)
        }
    }

    fun liked(games : Games){
        viewModelScope.launch {
            val result = getGameUseCase(games.id)

            when(result){
                is Result.Success ->{
                    dao.insertGames(result.data!!.toDatabase())
                    dao.insertDeals(result.data.deals.map { it.toDatabase() })
                }
                is Result.Error ->{
                    Log.d("Error", "Can't find game")
                }

                else -> {}
            }

        }
    }

    fun removeLiked(games : Games){
        viewModelScope.launch {
            dao.removeGame(games.id)
            println(dao.getAllDeals())
        }
    }

}