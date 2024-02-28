package com.example.rick_and_morty.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.repository.local.FavCharDatabase
import com.example.data.repository.local.FavCharRepositoryImpl
import com.example.data.repository.network.CharacterRepositoryImpl
import com.example.data.repository.network.RetrofitClient
import com.example.domain.usecases.AddToFavoritesUseCase
import com.example.domain.usecases.DeleteFromFavoritesUseCase
import com.example.domain.usecases.GetAllCharactersUseCase

class CharactersListVMFactory(private val context: Context) : ViewModelProvider.Factory {

    private val characterAPI by lazy { RetrofitClient.createCharacterApi() }
    private val favCharDao by lazy { FavCharDatabase.getDatabase(context).favCharDao() }
    private val characterRepository by lazy { CharacterRepositoryImpl(characterAPI) }
    private val favCharRepository by lazy { FavCharRepositoryImpl(favCharDao) }
    private val getAllCharactersUseCase by lazy { GetAllCharactersUseCase() }
    private val addToFavoritesUseCase by lazy { AddToFavoritesUseCase() }
    private val deleteFromFavoritesUseCase by lazy { DeleteFromFavoritesUseCase() }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CharactersListVM(
            characterRepository,
            favCharRepository,
            getAllCharactersUseCase,
            addToFavoritesUseCase,
            deleteFromFavoritesUseCase
            ) as T
    }
}