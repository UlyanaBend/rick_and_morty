package com.example.data.repository.network

import com.example.data.model.CharacterData
import com.example.data.model.Gender
import com.example.data.model.Origin
import com.example.domain.model.CharacterDomain
import com.example.domain.model.GenderDomain
import com.example.domain.model.OriginDomain
import com.example.domain.repository.CharacterRepository

class CharacterRepositoryImpl (private val characterAPI: CharacterAPI = RetrofitClient.createCharacterApi()): CharacterRepository {

    override suspend fun getCharacterById(id: Int): CharacterDomain {
        val characterData = characterAPI.getCharacterById(id)
        return mapCharacterToDomain(characterData)
    }

    override suspend fun getAllCharacters() : List<CharacterDomain> {
        return try {
            val characterDataList = characterAPI.getAllCharacters()
            return characterDataList.map { mapCharacterToDomain(it) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun mapCharacterToDomain(characterData: CharacterData): CharacterDomain {
        return CharacterDomain(
            id = characterData.id,
            name = characterData.name,
            species = characterData.species,
            gender = mapGenderToDomain(characterData.gender),
            origin = mapOriginToDomain(characterData.origin),
            image = characterData.image,
            isLiked = false
        )
    }

    private fun mapGenderToDomain(gender: Gender?): GenderDomain {
        return when (gender) {
            Gender.Female -> GenderDomain.Female
            Gender.Male -> GenderDomain.Male
            Gender.Genderless -> GenderDomain.Genderless
            Gender.Unknown -> GenderDomain.Unknown
            else -> GenderDomain.Unknown
        }
    }

    private fun mapOriginToDomain(origin: Origin): OriginDomain {
        return OriginDomain(
            name = origin.name,
            url = origin.url
        )
    }
}