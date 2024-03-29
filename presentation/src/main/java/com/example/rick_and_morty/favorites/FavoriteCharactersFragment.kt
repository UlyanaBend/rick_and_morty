package com.example.rick_and_morty.favorites

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rick_and_morty.adapters.FavoriteCharacterAdapter
import com.example.rick_and_morty.R
import com.example.rick_and_morty.app.App
import com.example.rick_and_morty.databinding.FragmentFavoriteCharactersBinding
import com.example.rick_and_morty.details.CharacterDetailsFragment
import com.example.rick_and_morty.main.CharactersListFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoriteCharactersFragment: Fragment() {

    private val binding: FragmentFavoriteCharactersBinding by lazy {
        FragmentFavoriteCharactersBinding.inflate(layoutInflater)
    }
    private val adapter: FavoriteCharacterAdapter by lazy {
        FavoriteCharacterAdapter()
    }

    @Inject
    lateinit var favoriteCharactersVMFactory: FavoriteCharactersVMFactory
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity().application as App).appComponent.injectFavorites(this)

        val vm: FavoriteCharactersVM = ViewModelProvider(this, favoriteCharactersVMFactory)
            .get(FavoriteCharactersVM::class.java)

        binding.rvCharList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCharList.adapter = adapter

        val noDataFavTextView: TextView = binding.noDataFavTextView

        val progressBar = binding.pbCharsList

        progressBar.visibility = View.VISIBLE
        binding.rvCharList.visibility = View.GONE

        lifecycleScope.launch {
            try {
                vm?.allFavCharacters()
            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "Error: ${e.message}")
            }
        }

        vm.allFavCharVMLive.observe(viewLifecycleOwner, Observer { characters ->
            progressBar.visibility = View.GONE
            binding.rvCharList.visibility = View.VISIBLE
            if (characters.isNullOrEmpty()) {
                progressBar.visibility = View.GONE
                noDataFavTextView.visibility = View.VISIBLE
                binding.rvCharList.visibility = View.GONE
            } else {
                noDataFavTextView.visibility = View.GONE
                adapter.submitList(characters)
            }
        })

        adapter.setOnItemClickListener { characterData ->
            val fragmentManager = requireActivity().supportFragmentManager
            val characterDetailsFragment = CharacterDetailsFragment()
            val bundle = Bundle()
            bundle.putInt("characterDataId", characterData.id)
            characterDetailsFragment.arguments = bundle
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    fragmentManager.beginTransaction()
                        .add(R.id.fragmentContainer, characterDetailsFragment)
                        .addToBackStack(null)
                        .commit()
                }
            }
        }

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            val currentFragment = parentFragmentManager.findFragmentById(R.id.fragmentContainer)
            val selectedFragment: Fragment = when (item.itemId) {
                R.id.menu_main -> CharactersListFragment()
                R.id.menu_favorite -> FavoriteCharactersFragment()
                else -> throw IllegalArgumentException("Unknown menu item ID: ${item.itemId}")
            }
            if (currentFragment == null || currentFragment::class != selectedFragment::class){
                parentFragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, selectedFragment)
                    .addToBackStack(null)
                    .commit()
            }
            true
        }
    }
}