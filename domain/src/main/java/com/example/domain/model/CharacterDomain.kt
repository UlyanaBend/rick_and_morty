package com.example.domain.model

data class CharacterDomain(
    val id: Int,
    val name: String,
    val species: String,
    val gender: DomainGender,
    val origin: DomainOrigin,
    val image: String,
    val episode: ArrayList<String>,
    val isLiked: Boolean = false
)

enum class DomainGender {
    Female, Male, Genderless, Unknown
}
data class DomainOrigin (
    val name : String,
    val url : String
)
