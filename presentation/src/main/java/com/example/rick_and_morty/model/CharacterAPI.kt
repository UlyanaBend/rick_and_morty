package com.example.rick_and_morty.model

import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterAPI {
    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): Character

    @GET("character/1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18")
    suspend fun getAllProducts() : List<Character>

    @GET("character/1,2,3,4,5,6,7")
    suspend fun getFavoriteProducts() : List<Character>
}