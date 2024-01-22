package com.example.rick_and_morty.model

data class Character(
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
    Female, Male, Genderless, unknown
}
data class Origin (
    val name : String,
    val url : String
)

data class Location (
    val name : String,
    val url : String
)