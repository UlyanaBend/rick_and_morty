package com.example.rick_and_morty.di

import android.content.Context
import com.example.data.repository.local.FavCharDao
import com.example.data.repository.local.FavCharDatabase
import com.example.data.repository.local.FavCharRepositoryImpl
import com.example.data.repository.network.CharacterAPI
import com.example.data.repository.network.CharacterRepositoryImpl
import com.example.data.repository.network.RetrofitClient
import com.example.domain.repository.CharacterRepository
import com.example.domain.repository.FavCharRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule (val context: Context) {

    @Provides
    fun provideCharacterApi(): CharacterAPI {
        return RetrofitClient.createCharacterApi()
    }
    @Provides
    fun provideFavCharDao(): FavCharDao {
        return FavCharDatabase.getDatabase(context).favCharDao()
    }
    @Provides
    fun provideCharacterRepository(characterAPI: CharacterAPI): CharacterRepository {
        return CharacterRepositoryImpl(characterAPI)
    }
    @Provides
    fun provideFavCharRepository(favCharDao: FavCharDao): FavCharRepository {
        return FavCharRepositoryImpl(favCharDao)
    }
}