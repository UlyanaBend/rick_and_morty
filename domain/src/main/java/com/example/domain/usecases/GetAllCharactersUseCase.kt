package com.example.domain.usecases

import com.example.domain.model.Character
import com.example.domain.model.Gender
import com.example.domain.model.Origin

class GetAllCharactersUseCase {
    fun execute() : List<Character> {
        return listOf(
            Character(
                id = 1,
                name = "Yana",
                species = "human",
                gender = Gender.Female,
                origin = Origin("OriginName", "OriginUrl"),
                image = "jfekljl",
                episode = arrayListOf("efefe"),
                isLiked = false
            ),
            Character(
                id = 2,
                name = "Yana2",
                species = "human",
                gender = Gender.Male,
                origin = Origin("OriginName", "OriginUrl"),
                image = "jfekljl",
                episode = arrayListOf("efefe"),
                isLiked = false
            ))
    }
}