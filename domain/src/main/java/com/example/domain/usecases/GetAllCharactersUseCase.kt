package com.example.domain.usecases

import com.example.domain.model.CharacterDomain
import com.example.domain.repository.CharacterRepository

class GetAllCharactersUseCase {
    suspend fun execute(characterRepository: CharacterRepository): List<CharacterDomain> {
        return characterRepository.getAllCharacters()
    }
}