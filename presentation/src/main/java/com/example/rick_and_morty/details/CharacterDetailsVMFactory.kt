package com.example.rick_and_morty.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.repository.CharacterRepositoryImpl
import com.example.data.repository.network.RetrofitClient
import com.example.domain.usecases.GetCharDetailsUseCase

class CharacterDetailsVMFactory : ViewModelProvider.Factory {

    private val getCharDetailsUseCase by lazy { GetCharDetailsUseCase() }
    private val characterAPI by lazy { RetrofitClient.createCharacterApi() }
    private val characterRepository by lazy { CharacterRepositoryImpl(characterAPI) }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CharacterDetailsVM(
            getCharDetailsUseCase,
            characterRepository) as T
    }

}