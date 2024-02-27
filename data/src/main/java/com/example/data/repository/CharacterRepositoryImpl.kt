package com.example.data.repository

import com.example.data.model.CharacterData
import com.example.data.model.Gender
import com.example.data.model.Origin
import com.example.data.repository.network.CharacterAPI
import com.example.data.repository.network.RetrofitClient
import com.example.domain.model.CharacterDomain
import com.example.domain.model.DomainGender
import com.example.domain.model.DomainOrigin
import com.example.domain.repository.CharacterRepository

class CharacterRepositoryImpl (private val characterAPI: CharacterAPI = RetrofitClient.createCharacterApi()): CharacterRepository {

    override suspend fun getCharacterById(id: Int): CharacterDomain {
        val characterData = characterAPI.getCharacterById(id)
        return mapToDomain(characterData)
    }

    override suspend fun getAllCharacters() : List<CharacterDomain> {
        val characterDataList = characterAPI.getAllCharacters()
        return characterDataList.map { mapToDomain(it) }
    }

    override suspend fun getFavCharacters(): List<CharacterDomain> {
        //val characterDataList = characterAPI.getAllCharacters()
        //return characterDataList.map { mapToDomain(it) }
        val emptyList: List<CharacterDomain> = emptyList()
        return emptyList
    }

    private fun mapToDomain(characterData: CharacterData): CharacterDomain {
        return CharacterDomain(
            id = characterData.id,
            name = characterData.name,
            species = characterData.species,
            gender = mapGenderToDomain(characterData.gender),
            origin = mapOriginToDomain(characterData.origin),
            image = characterData.image,
            episode = characterData.episode,
            isLiked = false
        )
    }

    private fun mapGenderToDomain(gender: Gender?): DomainGender {
        return when (gender) {
            Gender.Female -> DomainGender.Female
            Gender.Male -> DomainGender.Male
            Gender.Genderless -> DomainGender.Genderless
            Gender.Unknown -> DomainGender.Unknown
            else -> DomainGender.Unknown
        }
    }

    private fun mapOriginToDomain(origin: Origin): DomainOrigin {
        return DomainOrigin(
            name = origin.name,
            url = origin.url
        )
    }
}