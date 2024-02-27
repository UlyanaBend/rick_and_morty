package com.example.rick_and_morty.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.model.CharacterDomain
import com.example.domain.repository.CharacterRepository
import com.example.domain.usecases.GetAllCharactersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CharactersListVM(private val getAllCharactersUseCase: GetAllCharactersUseCase,
                       private val characterRepository :CharacterRepository
    ) : ViewModel() {

    private val allCharVMLiveMutable: MutableLiveData<List<CharacterDomain>> by lazy {
        MutableLiveData<List<CharacterDomain>>()
    }
    val allCharVMLive : MutableLiveData<List<CharacterDomain>> = allCharVMLiveMutable
    suspend fun allCharacters() {
        val characters = withContext(Dispatchers.IO) {
            getAllCharactersUseCase.execute(characterRepository)
        }
        allCharVMLiveMutable.postValue(characters)
    }
}