package com.example.rick_and_morty.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.model.CharacterDomain
import com.example.domain.repository.CharacterRepository
import com.example.domain.repository.FavCharRepository
import com.example.domain.usecases.AddToFavoritesUseCase
import com.example.domain.usecases.DeleteFromFavoritesUseCase
import com.example.domain.usecases.GetAllCharactersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CharactersListVM(
    private val characterRepository :CharacterRepository,
    private val favCharRepository: FavCharRepository,
    private val getAllCharactersUseCase: GetAllCharactersUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
    private val deleteFromFavoritesUseCase: DeleteFromFavoritesUseCase,
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

    suspend fun addToFavorites(){
        addToFavoritesUseCase.execute(favCharRepository)
    }

    suspend fun deleteFromFavorites(){
        deleteFromFavoritesUseCase.execute(favCharRepository)
    }
}