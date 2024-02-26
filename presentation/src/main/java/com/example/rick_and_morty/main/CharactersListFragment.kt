package com.example.rick_and_morty.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rick_and_morty.adapters.CharacterAdapter
import com.example.rick_and_morty.CharacterDetailsFragment
import com.example.rick_and_morty.CharacterItemClickListener
import com.example.rick_and_morty.favorites.FavoriteCharactersFragment
import com.example.rick_and_morty.R
import com.example.rick_and_morty.databinding.FragmentCharactersListBinding
import com.example.rick_and_morty.model.Character
import com.example.rick_and_morty.model.CharacterAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
class CharactersListFragment : Fragment() {

    private val binding: FragmentCharactersListBinding by lazy {
        FragmentCharactersListBinding.inflate(layoutInflater)
    }

    //improve
    private val adapter: CharacterAdapter by lazy {
        CharacterAdapter(characterList = emptyList(), object : CharacterItemClickListener {
            override fun onItemClick(character: Character) {
                val fragment = CharacterDetailsFragment.newInstance(character)
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        binding.rvCharList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCharList.adapter = adapter

        val retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/").client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
        val characterAPI = retrofit.create(CharacterAPI::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val list = characterAPI.getAllProducts()
            requireActivity().runOnUiThread {
                binding.apply {
                    adapter.submitList(list)
                }
            }
        }

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            val selectedFragment: Fragment = when (item.itemId) {
                R.id.menu_main -> {
                    CharactersListFragment()
                }
                R.id.menu_favorite -> {
                    FavoriteCharactersFragment()
                }
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