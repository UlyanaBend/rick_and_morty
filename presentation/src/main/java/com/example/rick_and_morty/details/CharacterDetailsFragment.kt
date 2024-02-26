package com.example.rick_and_morty.details

import androidx.fragment.app.Fragment
import com.example.domain.model.CharacterDomain

class CharacterDetailsFragment: Fragment() {
    companion object {
        fun newInstance(character: CharacterDomain): CharacterDetailsFragment {
            return CharacterDetailsFragment()
        }
    }
}