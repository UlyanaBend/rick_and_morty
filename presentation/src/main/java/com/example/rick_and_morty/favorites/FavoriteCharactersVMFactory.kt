package com.example.rick_and_morty.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.repository.CharacterRepositoryImpl
import com.example.data.repository.network.RetrofitClient
import com.example.domain.usecases.GetFavoritesUseCase

class FavoriteCharactersVMFactory : ViewModelProvider.Factory {

    private val getFavoritesUseCase by lazy { GetFavoritesUseCase() }
    private val characterAPI by lazy { RetrofitClient.createCharacterApi() }
    private val characterRepository by lazy { CharacterRepositoryImpl(characterAPI) }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoriteCharactersVM(
            getFavoritesUseCase,
            characterRepository) as T
    }

}