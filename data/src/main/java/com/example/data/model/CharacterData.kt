package com.example.data.model

data class CharacterData(
    val id : Int,
    val name : String,
    val status : Status,
    val species : String,
    val type : String,
    val gender : Gender,
    val origin : Origin,
    val location: Location,
    val image : String,
    val episode : ArrayList<String>,
    val url : String,
    val created : String
)

enum class Status {
    Alive, Dead, Unknown
}

enum class Gender {
    Female, Male, Genderless, Unknown
}
data class Origin (
    val name : String,
    val url : String
)

data class Location (
    val name : String,
    val url : String
)