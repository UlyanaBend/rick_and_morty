package com.example.domain.model

data class CharacterDomain(
    val id: Int,
    val name: String,
    val species: String,
    val gender: GenderDomain,
    val origin: OriginDomain,
    val image: String,
    val episode: ArrayList<String>,
    val isLiked: Boolean = false
)

enum class GenderDomain {
    Female, Male, Genderless, Unknown
}
data class OriginDomain (
    val name : String,
    val url : String
)
