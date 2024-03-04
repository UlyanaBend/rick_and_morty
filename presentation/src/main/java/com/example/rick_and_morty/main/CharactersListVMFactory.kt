package com.example.rick_and_morty.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.repository.local.FavCharDao
import com.example.data.repository.local.FavCharDatabase
import com.example.data.repository.local.FavCharRepositoryImpl
import com.example.data.repository.network.CharacterRepositoryImpl
import com.example.data.repository.network.RetrofitClient
import com.example.domain.repository.CharacterRepository
import com.example.domain.repository.FavCharRepository
import com.example.domain.usecases.AddToFavoritesUseCase
import com.example.domain.usecases.DeleteFromFavoritesUseCase
import com.example.domain.usecases.GetAllCharactersUseCase
import com.example.domain.usecases.GetFavoritesUseCase

class CharactersListVMFactory(
    private val context: Context,
    val addToFavoritesUseCase: AddToFavoritesUseCase,
    val deleteFromFavoritesUseCase: DeleteFromFavoritesUseCase,
    val getFavoritesUseCase: GetFavoritesUseCase,
    val favCharRepository: FavCharRepository,
    val getAllCharactersUseCase: GetAllCharactersUseCase,
    val characterRepository: CharacterRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CharactersListVM(
            characterRepository,
            favCharRepository,
            getAllCharactersUseCase,
            addToFavoritesUseCase,
            deleteFromFavoritesUseCase,
            getFavoritesUseCase
            ) as T
    }
}