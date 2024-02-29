package com.example.data.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.model.FavCharDataDB

@Database(entities = [FavCharDataDB::class], version = 3, exportSchema = false)
abstract class FavCharDatabase : RoomDatabase() {

    abstract fun favCharDao(): FavCharDao
    companion object {
        @Volatile
        private var INSTANCE: FavCharDatabase? = null
        fun getDatabase(context: Context): FavCharDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        FavCharDatabase:: class.java,
                        "fav_chars"
                    ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}