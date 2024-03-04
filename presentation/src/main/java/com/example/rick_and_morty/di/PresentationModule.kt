package com.example.rick_and_morty.di

import android.content.Context
import com.example.domain.repository.CharacterRepository
import com.example.domain.repository.FavCharRepository
import com.example.domain.usecases.AddToFavoritesUseCase
import com.example.domain.usecases.DeleteFromFavoritesUseCase
import com.example.domain.usecases.GetAllCharactersUseCase
import com.example.domain.usecases.GetCharDetailsUseCase
import com.example.domain.usecases.GetFavoritesUseCase
import com.example.rick_and_morty.details.CharacterDetailsVMFactory
import com.example.rick_and_morty.favorites.FavoriteCharactersVMFactory
import com.example.rick_and_morty.main.CharactersListVMFactory
import dagger.Module
import dagger.Provides

@Module
class PresentationModule(val context: Context) {
    @Provides
    fun provideContext(): Context{
        return context
    }

    @Provides
    fun provideCharactersListVMFactory(
        context: Context,
        addToFavoritesUseCase: AddToFavoritesUseCase,
        deleteFromFavoritesUseCase: DeleteFromFavoritesUseCase,
        getFavoritesUseCase: GetFavoritesUseCase,
        favCharRepository: FavCharRepository,
        getAllCharactersUseCase: GetAllCharactersUseCase,
        characterRepository: CharacterRepository
    ): CharactersListVMFactory {
        return CharactersListVMFactory(
            context,
            addToFavoritesUseCase,
            deleteFromFavoritesUseCase,
            getFavoritesUseCase,
            favCharRepository,
            getAllCharactersUseCase,
            characterRepository
        )
    }
    @Provides
    fun provideFavoriteCharactersVMFactory(
        context: Context,
        getFavoritesUseCase: GetFavoritesUseCase,
        favCharRepository: FavCharRepository
    ): FavoriteCharactersVMFactory {
        return FavoriteCharactersVMFactory(context, getFavoritesUseCase, favCharRepository)
    }
    @Provides
    fun provideCharacterDetailsVMFactory(
        getCharDetailsUseCase: GetCharDetailsUseCase,
        characterRepository: CharacterRepository
    ): CharacterDetailsVMFactory {
        return CharacterDetailsVMFactory(
            getCharDetailsUseCase,
            characterRepository)
    }
}