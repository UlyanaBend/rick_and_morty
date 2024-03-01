package com.example.domain.model

data class CharacterDomain(
    val id: Int,
    val name: String,
    val species: String,
    val gender: GenderDomain,
    val origin: OriginDomain,
    val image: String,
    var isLiked: Boolean = false
)

enum class GenderDomain {
    Female, Male, Genderless, Unknown;

    override fun toString(): String {
        return when (this) {
            Male -> "Male"
            Female -> "Female"
            Genderless -> "Genderless"
            else -> "Unknown"
        }
    }
}
data class OriginDomain (
    val name : String,
    val url : String
){
    override fun toString(): String {
        return name
    }
}
