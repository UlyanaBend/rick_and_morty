package com.example.rick_and_morty.main

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rick_and_morty.adapters.CharacterAdapter
import com.example.rick_and_morty.favorites.FavoriteCharactersFragment
import com.example.rick_and_morty.R
import com.example.rick_and_morty.databinding.FragmentCharactersListBinding
import com.example.data.repository.CharacterRepositoryImpl
import com.example.data.repository.network.RetrofitClient
import com.example.domain.usecases.GetAllCharactersUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
class CharactersListFragment : Fragment() {

    private val binding: FragmentCharactersListBinding by lazy {
        FragmentCharactersListBinding.inflate(layoutInflater)
    }

    private val adapter: CharacterAdapter by lazy {
        CharacterAdapter ()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val getAllCharactersUseCase = GetAllCharactersUseCase()
        val characterAPI = RetrofitClient.createCharacterApi()
        val characterRepository = CharacterRepositoryImpl(characterAPI)

        CoroutineScope(Dispatchers.IO).launch {
            val list = characterRepository.getAllCharacters()
            requireActivity().runOnUiThread {
                binding.apply {
                    adapter.submitList(list)
                }
            }
        }

        binding.rvCharList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCharList.adapter = adapter

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val list = getAllCharactersUseCase.execute(characterRepository)
                requireActivity().runOnUiThread {
                    binding.apply {
                        adapter.submitList(list)
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error: ${e.message}")
            }
        }

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