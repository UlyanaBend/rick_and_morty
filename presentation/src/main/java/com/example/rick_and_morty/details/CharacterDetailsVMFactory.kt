package com.example.rick_and_morty.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.repository.network.CharacterRepositoryImpl
import com.example.data.repository.network.RetrofitClient
import com.example.domain.repository.CharacterRepository
import com.example.domain.usecases.GetCharDetailsUseCase

class CharacterDetailsVMFactory(
    val getCharDetailsUseCase: GetCharDetailsUseCase,
    val characterRepository: CharacterRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CharacterDetailsVM(
            getCharDetailsUseCase,
            characterRepository) as T
    }

}