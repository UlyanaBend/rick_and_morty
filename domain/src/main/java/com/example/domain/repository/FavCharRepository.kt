package com.example.domain.repository

import com.example.domain.model.CharacterDomain

interface FavCharRepository {
    suspend fun upsertFavChar(characterDomain : CharacterDomain)
    suspend fun deleteFavChar(characterDomain : CharacterDomain)
    fun getFavChars(): List<CharacterDomain>
}
