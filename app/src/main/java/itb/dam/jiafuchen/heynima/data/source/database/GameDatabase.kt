package itb.dam.jiafuchen.heynima.data.source.database

import androidx.room.Database
import androidx.room.RoomDatabase
import itb.dam.jiafuchen.heynima.data.source.database.dao.GameDao
import itb.dam.jiafuchen.heynima.data.source.database.entity.DealEntity
import itb.dam.jiafuchen.heynima.data.source.database.entity.GameEntity
import itb.dam.jiafuchen.heynima.data.source.database.entity.StoreEntity

@Database(entities = [GameEntity::class, DealEntity::class, StoreEntity::class], version = 5)
abstract class GameDatabase : RoomDatabase(){
    abstract fun getGameDao() : GameDao
}