package com.example.domain.usecases

import com.example.domain.model.CharacterDomain
import com.example.domain.model.GenderDomain
import com.example.domain.model.OriginDomain
import com.example.domain.repository.FavCharRepository

class DeleteFromFavoritesUseCase {
    suspend fun execute(characterDomain: CharacterDomain, favCharRepository: FavCharRepository) {
        favCharRepository.deleteFavChar(characterDomain)
    }
}