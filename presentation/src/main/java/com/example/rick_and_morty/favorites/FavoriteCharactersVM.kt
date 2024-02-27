package com.example.rick_and_morty.favorites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.model.CharacterDomain
import com.example.domain.repository.CharacterRepository
import com.example.domain.usecases.GetFavoritesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoriteCharactersVM(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val characterRepository : CharacterRepository
    ) : ViewModel() {

        private val allFavCharVMLiveMutable: MutableLiveData<List<CharacterDomain>> by lazy {
            MutableLiveData<List<CharacterDomain>>()
        }
        val allFavCharVMLive : MutableLiveData<List<CharacterDomain>> = allFavCharVMLiveMutable
        suspend fun allFavCharacters() {
            val characters = withContext(Dispatchers.IO) {
                getFavoritesUseCase.execute(characterRepository)
            }
            allFavCharVMLiveMutable.postValue(characters)
        }
    }