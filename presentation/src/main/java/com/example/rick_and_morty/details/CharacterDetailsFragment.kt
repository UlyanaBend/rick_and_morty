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
import com.bumptech.glide.Glide
import com.example.rick_and_morty.R
import com.example.rick_and_morty.databinding.FragmentCharacterDetailsBinding
import com.example.rick_and_morty.favorites.FavoriteCharactersFragment
import com.example.rick_and_morty.main.CharactersListFragment
import kotlinx.coroutines.launch

class CharacterDetailsFragment: Fragment() {

    private val binding: FragmentCharacterDetailsBinding by lazy {
        FragmentCharacterDetailsBinding.inflate(layoutInflater)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) : Unit {

        val vm: CharacterDetailsVM = ViewModelProvider(this, CharacterDetailsVMFactory())
            .get(CharacterDetailsVM::class.java)

        vm.charImageVMLive.observe(viewLifecycleOwner, Observer { charImage ->
            with(binding) {
                Glide.with(requireContext())
                    .load(charImage)
                    .into(ivDetails)
            }
        })
        vm.charNameVMLive.observe(viewLifecycleOwner, Observer { charName ->
            binding.nameDetails.text = charName
        })
        vm.charSpeciesVMLive.observe(viewLifecycleOwner, Observer { charSpecies ->
            binding.speciesDetails.text = charSpecies
        })
        vm.charGenderVMLive.observe(viewLifecycleOwner, Observer { charGender ->
            binding.genderDetails.text = charGender
        })
        vm.charOriginVMLive.observe(viewLifecycleOwner, Observer { charOrigin ->
            binding.originDetails.text = charOrigin
        })

        val args = arguments
        if (args != null) {
            val characterDataId = args.getInt("characterDataId")
            binding.nameDetails.text = vm.charNameVMLive.value
            lifecycleScope.launch {
                try {
                    vm.getCharDetails(characterDataId)
                } catch (e: Exception) {
                    Log.e(ContentValues.TAG, "Error: ${e.message}")
                }
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