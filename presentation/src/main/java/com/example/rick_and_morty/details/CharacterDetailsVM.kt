package com.example.rick_and_morty.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.model.CharacterDomain
import com.example.domain.repository.CharacterRepository
import com.example.domain.usecases.GetCharDetailsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CharacterDetailsVM (
    private val getCharDetailsUseCase: GetCharDetailsUseCase,
    private val characterRepository : CharacterRepository
) : ViewModel() {

    private val charDetailsMVLiveMutable: MutableLiveData<CharacterDomain> by lazy {
        MutableLiveData<CharacterDomain>()
    }

    val charDetailsVMLive: LiveData<CharacterDomain> = charDetailsMVLiveMutable

    suspend fun getCharDetails() {
        val character = withContext(Dispatchers.IO) {
            getCharDetailsUseCase.execute(characterRepository)
        }
        charDetailsMVLiveMutable.postValue(character)
    }
}
