package com.example.rick_and_morty.main

import android.content.ContentValues.TAG
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
import com.example.rick_and_morty.adapters.CharacterAdapter
import com.example.rick_and_morty.favorites.FavoriteCharactersFragment
import com.example.rick_and_morty.R
import com.example.rick_and_morty.databinding.FragmentCharactersListBinding
import com.example.rick_and_morty.details.CharacterDetailsFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

        val vm: CharactersListVM = ViewModelProvider(this, CharactersListVMFactory(requireContext()))
            .get(CharactersListVM::class.java)

        binding.rvCharList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCharList.adapter = adapter

        val noDataTextView: TextView = binding.noDataTextView
        val swipeRefreshLayout = binding.swipeRefreshLayout

        val progressBar = binding.pbCharsList

        progressBar.visibility = View.VISIBLE
        binding.rvCharList.visibility = View.GONE

        lifecycleScope.launch {
            try {
                vm?.allCharacters()
            } catch (e: Exception) {
                Log.e(TAG, "Error: ${e.message}")
            }
        }

        vm.currentAllCharsVMLive.observe(viewLifecycleOwner, Observer { characters ->
            progressBar.visibility = View.GONE
            binding.rvCharList.visibility = View.VISIBLE
            if (characters.isNullOrEmpty()) {
                noDataTextView.visibility = View.VISIBLE
                binding.pbCharsListCont.visibility = View.GONE
                binding.rvCharList.visibility = View.GONE
            } else {
                noDataTextView.visibility = View.GONE
                adapter.submitList(characters)
            }
        })

        swipeRefreshLayout.setOnRefreshListener {
            vm.currentAllCharsVMLive.value?.let { characters ->
                if (characters.isNullOrEmpty()) {
                    noDataTextView.visibility = View.VISIBLE
                    binding.pbCharsListCont.visibility = View.GONE
                    binding.rvCharList.visibility = View.GONE
                } else {
                    noDataTextView.visibility = View.GONE
                    binding.rvCharList.visibility = View.VISIBLE
                    adapter.submitList(characters)
                }
            }
//            noDataTextView.visibility = View.VISIBLE
//            binding.pbCharsListCont.visibility = View.GONE
//            binding.rvCharList.visibility = View.GONE
            swipeRefreshLayout.isRefreshing = false
        }

        adapter.setOnFavoriteClickListener { characterData ->
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    if (!characterData.isLiked) { vm.addToFavorites(characterData.id) }
                    else { vm.deleteFromFavorites(characterData.id) }
                }
            }
        }

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