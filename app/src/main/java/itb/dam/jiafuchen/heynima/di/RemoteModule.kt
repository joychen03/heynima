package itb.dam.jiafuchen.heynima.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import itb.dam.jiafuchen.heynima.data.source.remote.CheapSharkApi
import itb.dam.jiafuchen.heynima.domain.models.Store
import itb.dam.jiafuchen.heynima.utils.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    var stores : List<Store> = listOf()

    @Provides
    @Singleton
    fun provideCheapSharkApi() : CheapSharkApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CheapSharkApi::class.java)
    }

//    @Provides
//    @Singleton
//    fun provideStores(repository: GameRepository) : List<Stores>{
//
//        if(stores.isEmpty()){
//            GlobalScope.launch {
//                repository.getStores().onEach { result ->
//                    when(result){
//                        is Result.Success ->{
//                            println(result.data!!)
//                            stores = result.data!!
//                        }
//                        is Result.Error->{
//                            Log.d("Error", result.message!!)
//                        }
//                    }
//                }.launchIn(this)
//            }
//        }
//
//        return stores;
//    }
}