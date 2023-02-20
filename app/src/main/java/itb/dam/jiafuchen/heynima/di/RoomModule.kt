package itb.dam.jiafuchen.heynima.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import itb.dam.jiafuchen.heynima.data.source.database.GameDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    const val DATABSE_NAME = "game_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        GameDatabase::class.java,
        DATABSE_NAME
    ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideGameDAO(db : GameDatabase) = db.getGameDao()
}