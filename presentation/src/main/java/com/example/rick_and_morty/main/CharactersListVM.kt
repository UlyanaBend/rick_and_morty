package com.example.rick_and_morty.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.model.CharacterDomain
import com.example.domain.repository.CharacterRepository
import com.example.domain.repository.FavCharRepository
import com.example.domain.usecases.AddToFavoritesUseCase
import com.example.domain.usecases.DeleteFromFavoritesUseCase
import com.example.domain.usecases.GetAllCharactersUseCase
import com.example.domain.usecases.GetFavoritesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CharactersListVM(
    private val characterRepository :CharacterRepository,
    private val favCharRepository: FavCharRepository,
    private val getAllCharactersUseCase: GetAllCharactersUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
    private val deleteFromFavoritesUseCase: DeleteFromFavoritesUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase
    ) : ViewModel() {

    private val currentAllCharsVMLiveMutable: MutableLiveData<List<CharacterDomain>> by lazy {
        MutableLiveData<List<CharacterDomain>>()
    }
    val currentAllCharsVMLive : LiveData<List<CharacterDomain>> = currentAllCharsVMLiveMutable
    suspend fun allCharacters() {
        val characters = withContext(Dispatchers.IO) {
            val allExistChars = getAllCharactersUseCase.execute(characterRepository)
            val initFavChars = getFavoritesUseCase.execute(favCharRepository)
            for (initChar in initFavChars) {
                for (existChar in allExistChars) {
                    if (initChar.id == existChar.id) {
                        existChar.isLiked = true
                        break
                    }
                }
            }
            return@withContext allExistChars
        }
        currentAllCharsVMLiveMutable.postValue(characters)
    }

    suspend fun addToFavorites(id:Int){
        addToFavoritesUseCase.execute(characterRepository.getCharacterById(id), favCharRepository)
        allCharacters()
        updateCharacterLikedStatus(id,true,currentAllCharsVMLiveMutable)
    }

    suspend fun deleteFromFavorites(id:Int){
        deleteFromFavoritesUseCase.execute(characterRepository.getCharacterById(id), favCharRepository)
        allCharacters()
        updateCharacterLikedStatus(id,false,currentAllCharsVMLiveMutable)
    }

    private fun updateCharacterLikedStatus(
        id: Int,
        isLiked: Boolean,
        liveData: MutableLiveData<List<CharacterDomain>>
    ) {
        val currentList = liveData.value ?: return
        val updatedList = currentList.map { character ->
            if (character.id == id) {
                character.copy(isLiked = isLiked)
            } else {
                character
            }
        }
        liveData.postValue(updatedList)
    }
}