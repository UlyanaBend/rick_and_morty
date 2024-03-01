package com.example.rick_and_morty.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.repository.CharacterRepository
import com.example.domain.usecases.GetCharDetailsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CharacterDetailsVM (
    private val getCharDetailsUseCase: GetCharDetailsUseCase,
    private val characterRepository : CharacterRepository
) : ViewModel() {

    private val charImageMVLiveMutable: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    private val charNameMVLiveMutable: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    private val charSpeciesMVLiveMutable: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    private val charGenderMVLiveMutable: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    private val charOriginMVLiveMutable: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val charImageVMLive: LiveData<String> = charImageMVLiveMutable
    val charNameVMLive: LiveData<String> = charNameMVLiveMutable
    val charSpeciesVMLive: LiveData<String> = charSpeciesMVLiveMutable
    val charGenderVMLive: LiveData<String> = charGenderMVLiveMutable
    val charOriginVMLive: LiveData<String> = charOriginMVLiveMutable

    suspend fun getCharDetails(id : Int) {
        val character = withContext(Dispatchers.IO) {
            getCharDetailsUseCase.execute(id, characterRepository)
        }
        charImageMVLiveMutable.postValue(character.image)
        charNameMVLiveMutable.postValue(character.name)
        charSpeciesMVLiveMutable.postValue(character.species)
        charGenderMVLiveMutable.postValue(character.gender.toString())
        charOriginMVLiveMutable.postValue(character.origin.toString())
    }
}
