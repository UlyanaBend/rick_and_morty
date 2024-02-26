package com.example.rick_and_morty.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.rick_and_morty.adapters.FavoriteCharacterAdapter
import com.example.rick_and_morty.R
import com.example.rick_and_morty.databinding.FragmentFavoriteCharactersBinding
import com.example.rick_and_morty.main.CharactersListFragment

class FavoriteCharactersFragment: Fragment() {

    private val binding: FragmentFavoriteCharactersBinding by lazy {
        FragmentFavoriteCharactersBinding.inflate(layoutInflater)
    }
    private val adapter: FavoriteCharacterAdapter by lazy {
        FavoriteCharacterAdapter()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            val selectedFragment: Fragment = when (item.itemId) {
                R.id.menu_main -> CharactersListFragment()
                R.id.menu_favorite -> FavoriteCharactersFragment()
                else -> throw IllegalArgumentException("Unknown menu item ID: ${item.itemId}")
            }
            parentFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, selectedFragment)
                .addToBackStack(null)
                .commit()
            true
        }
    }
}