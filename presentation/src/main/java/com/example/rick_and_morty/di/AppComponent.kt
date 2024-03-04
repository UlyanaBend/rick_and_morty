package com.example.rick_and_morty.di

import com.example.data.repository.local.FavCharDao
import com.example.data.repository.network.CharacterAPI
import com.example.domain.repository.CharacterRepository
import com.example.domain.repository.FavCharRepository
import com.example.rick_and_morty.details.CharacterDetailsFragment
import com.example.rick_and_morty.favorites.FavoriteCharactersFragment
import com.example.rick_and_morty.main.CharactersListFragment
import dagger.Component

@Component(modules = [PresentationModule::class, DomainModule::class, DataModule::class])
interface AppComponent {
    fun injectMain(charactersListFragment: CharactersListFragment)
    fun injectFavorites(favoriteCharactersFragment: FavoriteCharactersFragment)
    fun injectDetails(characterDetailsFragment: CharacterDetailsFragment)


}