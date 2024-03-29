package com.example.domain.usecases

import com.example.domain.model.CharacterDomain
import com.example.domain.repository.CharacterRepository

class GetCharDetailsUseCase {
    suspend fun execute(id : Int, characterRepository: CharacterRepository): CharacterDomain {
        return characterRepository.getCharacterById(id)
    }
}