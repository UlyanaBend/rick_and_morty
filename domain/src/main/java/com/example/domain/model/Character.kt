package com.example.domain.model

data class Character(
    val id : Int,
    val name : String,
    val species : String,
    val gender : Gender,
    val origin : Origin,
    val image : String,
    val episode : ArrayList<String>,
    val isLiked : Boolean = false
)

enum class Gender {
    Female, Male, Genderless, Unknown
}
data class Origin (
    val name : String,
    val url : String
)
