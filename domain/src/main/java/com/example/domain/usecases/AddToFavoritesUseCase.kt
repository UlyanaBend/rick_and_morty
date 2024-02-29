package com.example.domain.usecases

import com.example.domain.model.CharacterDomain
import com.example.domain.model.GenderDomain
import com.example.domain.model.OriginDomain
import com.example.domain.repository.FavCharRepository
import java.util.ArrayList

class AddToFavoritesUseCase {
    suspend fun execute(characterDomain: CharacterDomain, favCharRepository: FavCharRepository) {
        favCharRepository.upsertFavChar(characterDomain)
    }
}