package com.example.domain.usecases

import com.example.domain.model.CharacterDomain
import com.example.domain.repository.FavCharRepository

class GetFavoritesUseCase {
    fun execute(favCharRepository: FavCharRepository): List<CharacterDomain> {
        return favCharRepository.getFavChars()
    }
}