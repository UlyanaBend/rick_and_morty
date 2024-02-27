package com.example.rick_and_morty.details

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.model.CharacterDomain
import com.example.rick_and_morty.R
import com.example.rick_and_morty.adapters.FavoriteCharacterAdapter
import com.example.rick_and_morty.databinding.FragmentFavoriteCharactersBinding
import com.example.rick_and_morty.favorites.FavoriteCharactersFragment
import com.example.rick_and_morty.favorites.FavoriteCharactersVM
import com.example.rick_and_morty.favorites.FavoriteCharactersVMFactory
import com.example.rick_and_morty.main.CharactersListFragment
import kotlinx.coroutines.launch

class CharacterDetailsFragment: Fragment() {
    companion object {
        fun newInstance(character: CharacterDomain): CharacterDetailsFragment {
            return CharacterDetailsFragment()
        }
    }

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

        val vm: CharacterDetailsVM = ViewModelProvider(this, CharacterDetailsVMFactory())
            .get(CharacterDetailsVM::class.java)

        binding.rvCharList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCharList.adapter = adapter

        lifecycleScope.launch {
            try {
                vm.getCharDetails()
            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "Error: ${e.message}")
            }
        }

        vm.charDetailsVMLive.observe(viewLifecycleOwner, Observer {
        })

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