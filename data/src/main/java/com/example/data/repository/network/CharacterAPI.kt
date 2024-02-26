package com.example.data.repository.network

import com.example.data.model.CharacterData
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterAPI {
    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): CharacterData

    @GET("character/1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20")
    suspend fun getAllCharacters() : List<CharacterData>
}

