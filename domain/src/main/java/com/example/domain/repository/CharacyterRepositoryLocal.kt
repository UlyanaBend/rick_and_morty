package com.example.domain.repository

import com.example.domain.model.CharacterDomain
import kotlinx.coroutines.flow.Flow

interface CharacterRepositoryLocal {
    suspend fun upsertCharacter(character: CharacterDomain)

    suspend fun deleteCharacter(character: CharacterDomain)

    fun getFavChars(): Flow<List<CharacterDomain>>

}