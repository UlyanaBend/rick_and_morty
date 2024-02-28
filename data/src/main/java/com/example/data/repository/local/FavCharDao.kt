package com.example.data.repository.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.data.model.FavCharDataDB

@Dao
interface FavCharDao {

    @Upsert
    suspend fun addFavChar(favCharDataDB: FavCharDataDB)

    @Query("SELECT * FROM fav_chars")
    fun readAllData () : List<FavCharDataDB>

    @Delete
    fun deleteFavChar(favCharDataDB: FavCharDataDB)
}