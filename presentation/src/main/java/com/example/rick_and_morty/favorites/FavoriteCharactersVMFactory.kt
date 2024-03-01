package com.example.rick_and_morty.favorites

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.repository.local.FavCharDatabase
import com.example.data.repository.local.FavCharRepositoryImpl
import com.example.domain.usecases.GetFavoritesUseCase

class FavoriteCharactersVMFactory(private val context: Context) : ViewModelProvider.Factory {

    private val getFavoritesUseCase by lazy { GetFavoritesUseCase() }
    private val favCharDao by lazy { FavCharDatabase.getDatabase(context).favCharDao() } //Room
    private val favCharRepository by lazy { FavCharRepositoryImpl(favCharDao) }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoriteCharactersVM(
            getFavoritesUseCase,
            favCharRepository) as T
    }

}