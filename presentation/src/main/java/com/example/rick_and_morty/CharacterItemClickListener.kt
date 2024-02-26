package com.example.rick_and_morty

import com.example.rick_and_morty.model.Character

interface CharacterItemClickListener {
    fun onItemClick(character: Character)
}