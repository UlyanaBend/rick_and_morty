package com.example.data.repository.local

import com.example.data.model.FavCharDataDB
import com.example.domain.model.CharacterDomain
import com.example.domain.model.GenderDomain
import com.example.domain.model.OriginDomain
import com.example.domain.repository.FavCharRepository

class FavCharRepositoryImpl (private val favCharDao: FavCharDao): FavCharRepository {

    override suspend fun upsertFavChar(characterDomain: CharacterDomain) {
        favCharDao.addFavChar(mapCharacterDomainToFavCharDataDB(characterDomain))
    }
    override suspend fun deleteFavChar(characterDomain: CharacterDomain) {
        favCharDao.deleteFavChar(mapCharacterDomainToFavCharDataDB(characterDomain))
    }
    override fun getFavChars(): List<CharacterDomain> {
        val readAllData: List<FavCharDataDB> = favCharDao.readAllData()
        return readAllData.map { mapFavCharToDomain(it) }
    }

    private fun mapFavCharToDomain(favCharDataDB: FavCharDataDB): CharacterDomain {
        return CharacterDomain(
            id = favCharDataDB.id,
            name = favCharDataDB.name,
            species = favCharDataDB.species,
            gender = mapGenderDataDBToDomain(favCharDataDB.gender),
            origin = mapOriginDataDBToDomain(favCharDataDB.origin),
            image = favCharDataDB.image,
            episode = mapEpisodeDataDBToDomain(favCharDataDB.episode),
            isLiked = false
        )
    }

    private fun mapGenderDataDBToDomain(genderDataDB: String?): GenderDomain {
        return when (genderDataDB) {
            "Female" -> GenderDomain.Female
            "Male" -> GenderDomain.Male
            "Genderless" -> GenderDomain.Genderless
            "Unknown" -> GenderDomain.Unknown
            else -> GenderDomain.Unknown
        }
    }

    private fun mapOriginDataDBToDomain(originDataDB: String): OriginDomain {
        val parts = originDataDB.split("|")
        val name = if (parts.isNotEmpty()) parts[0] else ""
        val url = if (parts.size > 1) parts[1] else ""
        return OriginDomain(name, url)
    }

    private fun mapEpisodeDataDBToDomain(episodeDataDB: String): ArrayList<String> {
        return ArrayList(episodeDataDB.split(","))
    }

    private fun mapCharacterDomainToFavCharDataDB(characterDomain: CharacterDomain): FavCharDataDB {
        return FavCharDataDB (
            id = characterDomain.id,
            name = characterDomain.name,
            species = characterDomain.species,
            gender = mapGenderDomainToGenderDB(characterDomain.gender),
            origin = mapOriginDomainToFavCharDataDB(characterDomain.origin),
            image = characterDomain.image,
            episode = mapEpisodeDomainToDataDB(characterDomain.episode)
        )
    }

    private fun mapGenderDomainToGenderDB(genderDomain : GenderDomain): String {
        return when (genderDomain) {
            GenderDomain.Female -> "Female"
            GenderDomain.Male -> "Male"
            GenderDomain.Genderless -> "Genderless"
            GenderDomain.Unknown -> "Unknown"
        }
    }

    private fun mapOriginDomainToFavCharDataDB(originDomain: OriginDomain): String {
        return "${originDomain.name}|${originDomain.url}"
    }

    private fun mapEpisodeDomainToDataDB(episodeDomain: ArrayList<String>): String {
        return episodeDomain.joinToString(",")
    }
}