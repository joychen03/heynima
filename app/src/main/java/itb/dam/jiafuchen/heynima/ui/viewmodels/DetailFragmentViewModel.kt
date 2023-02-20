package itb.dam.jiafuchen.heynima.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import itb.dam.jiafuchen.heynima.data.Result
import itb.dam.jiafuchen.heynima.data.source.database.dao.GameDao
import itb.dam.jiafuchen.heynima.data.source.database.relations.toDomain
import itb.dam.jiafuchen.heynima.data.source.remote.dto.toDatabase
import itb.dam.jiafuchen.heynima.domain.models.Game
import itb.dam.jiafuchen.heynima.domain.models.Store
import itb.dam.jiafuchen.heynima.domain.models.toDatabase
import itb.dam.jiafuchen.heynima.domain.usecases.GetGameUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailFragmentViewModel @Inject constructor(
    private val getGameUseCase: GetGameUseCase,
    private val dao : GameDao
) : ViewModel() {

    var storeList : List<Store> = emptyList()

    private val _game = MutableLiveData<Game>()
    val game : LiveData<Game> = _game

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage : LiveData<String> = _errorMessage

    private val _isFavourite = MutableLiveData<Boolean>()
    val isFavourite : LiveData<Boolean> = _isFavourite


    fun getGame(gameID : String){

        viewModelScope.launch {

            _isLoading.postValue(true)

            val result = getGameUseCase(gameID)

            when(result){
                is Result.Success ->{
                    val favGame = dao.getGamesWithID(gameID)

                    if(favGame != null && result.data?.id == favGame.gameID){
                        _isFavourite.postValue(true)
                    }
                    _game.postValue(result.data!!)
                    _isLoading.postValue(false)
                }
                is Result.Error ->{
                    val localResult = dao.getGameWithDealsAndStoresWithID(gameID)?.toDomain()
                    if(localResult != null){
                        _game.postValue(localResult)
                        _isFavourite.postValue(true)
                    }else{
                        _errorMessage.postValue(result.message!!)
                    }
                    _isLoading.postValue(false)
                }
                is Result.Loading->{
                    _isLoading.postValue(true)
                }
            }

        }
    }

    fun likeGame(){
        viewModelScope.launch {
            dao.insertGames(game.value!!.toDatabase())
            dao.insertDeals(game.value!!.deals.map { it.toDatabase() })
        }
    }

    fun removeLikeGame(){
        viewModelScope.launch {
            dao.removeGame(game.value!!.id)
        }
    }
}