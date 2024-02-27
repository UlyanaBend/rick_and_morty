package com.example.rick_and_morty.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.repository.CharacterRepositoryImpl
import com.example.data.repository.network.RetrofitClient
import com.example.domain.usecases.GetAllCharactersUseCase

class CharactersListVMFactory (private val context: Context): ViewModelProvider.Factory {

    private val getAllCharactersUseCase by lazy { GetAllCharactersUseCase() }
    private val characterAPI by lazy { RetrofitClient.createCharacterApi() }
    private val characterRepository by lazy { CharacterRepositoryImpl(characterAPI) }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CharactersListVM(
            getAllCharactersUseCase,
            characterRepository) as T
    }
}