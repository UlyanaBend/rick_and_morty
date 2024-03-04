package com.example.rick_and_morty.di

import com.example.domain.repository.CharacterRepository
import com.example.domain.repository.FavCharRepository
import com.example.domain.usecases.AddToFavoritesUseCase
import com.example.domain.usecases.DeleteFromFavoritesUseCase
import com.example.domain.usecases.GetAllCharactersUseCase
import com.example.domain.usecases.GetCharDetailsUseCase
import com.example.domain.usecases.GetFavoritesUseCase
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideGetAllCharactersUseCase(characterRepository: CharacterRepository): GetAllCharactersUseCase {
        return GetAllCharactersUseCase()
    }

    @Provides
    fun provideGetCharDetailsUseCase(): GetCharDetailsUseCase {
        return GetCharDetailsUseCase()
    }

    @Provides
    fun provideAddToFavoritesUseCase(favCharRepository: FavCharRepository): AddToFavoritesUseCase {
        return AddToFavoritesUseCase()
    }

    @Provides
    fun provideDeleteFromFavoritesUseCase(favCharRepository: FavCharRepository): DeleteFromFavoritesUseCase {
        return DeleteFromFavoritesUseCase()
    }

    @Provides
    fun provideGetFavoritesUseCase(favCharRepository: FavCharRepository): GetFavoritesUseCase {
        return GetFavoritesUseCase()
    }
}