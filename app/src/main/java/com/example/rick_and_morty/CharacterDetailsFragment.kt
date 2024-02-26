package com.example.rick_and_morty

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.rick_and_morty.model.Character

class CharacterDetailsFragment: Fragment() {
    companion object {
        fun newInstance(character: Character): CharacterDetailsFragment {
            val fragment = CharacterDetailsFragment()
            val args = Bundle()
            args.putSerializable("character", character)
            fragment.arguments = args
            return fragment
        }
    }
}

private fun Bundle.putSerializable(s: String, character: Character) {

}
