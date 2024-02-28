package com.example.domain.repository

import com.example.domain.model.CharacterDomain

interface CharacterRepository {
    suspend fun getCharacterById(id: Int): CharacterDomain
    suspend fun getAllCharacters() : List<CharacterDomain>
}