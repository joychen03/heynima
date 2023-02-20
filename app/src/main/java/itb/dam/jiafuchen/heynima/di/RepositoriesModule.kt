package itb.dam.jiafuchen.heynima.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import itb.dam.jiafuchen.heynima.data.repositories.GameRepositoryImpl
import itb.dam.jiafuchen.heynima.domain.repositories.GameRepository

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoriesModule {

    @Binds
    abstract fun bindGameRepository(impl : GameRepositoryImpl) : GameRepository

}