package itb.dam.jiafuchen.heynima.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import itb.dam.jiafuchen.heynima.data.Result
import itb.dam.jiafuchen.heynima.data.source.database.dao.GameDao
import itb.dam.jiafuchen.heynima.data.source.database.entity.toDomain
import itb.dam.jiafuchen.heynima.domain.models.Store
import itb.dam.jiafuchen.heynima.domain.usecases.GetStoresUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val getStoresUseCase: GetStoresUseCase,
    private val dao: GameDao
) : ViewModel() {

    var storeList : List<Store> = emptyList()
    var currentSearch : String = ""
    var currentGameLookup : String = ""

    var listFragmentItemScrollPosition = 0
    var listFragmentItemScrollOffset = 0

    var favGameListFragmentItemScrollPosition = 0
    var favGameListFragmentItemScrollOffset = 0

    fun getStores() {
        if(storeList.isNotEmpty()) return

        viewModelScope.launch {

            getStoresUseCase().onEach { result ->
                when(result){
                    is Result.Success ->{
                        storeList = result.data!!
                    }
                    is Result.Error ->{
                        storeList = dao.getAllStores().map { it.toDomain() }
                        Log.d("Error", "GetStores failed: ${result.message}")
                    }
                }
            }.launchIn(this)
        }
    }

    fun saveSearch(game : String){
        currentSearch = game
        listFragmentItemScrollPosition = 0
        listFragmentItemScrollOffset = 0
    }

    fun gameLookUp(gameID : String){
        currentGameLookup = gameID
    }

    fun listFragmentScrollTo(indexItemRV: Int, topViewRV: Int) {
        listFragmentItemScrollPosition = indexItemRV
        listFragmentItemScrollOffset = topViewRV
    }

    fun favGameListFragmentScrollTo(indexItemRV: Int, topViewRV: Int) {
        favGameListFragmentItemScrollPosition = indexItemRV
        favGameListFragmentItemScrollOffset = topViewRV
    }

}