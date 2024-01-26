package com.example.rick_and_morty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.rick_and_morty.model.CharacterAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CharactersListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val rootView = inflater.inflate(R.layout.fragment_characters_list, container, false)
        val tvModel = rootView.findViewById<TextView>(R.id.tvModel)
        val imgAvatar = rootView.findViewById<ImageView>(R.id.ivAvatar)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/").client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
        val characterAPI = retrofit.create(CharacterAPI::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val character = characterAPI.getCharacterById(180)
            requireActivity().runOnUiThread {
                tvModel.text = character.name
                Glide.with(this@CharactersListFragment)
                    .load(character.image)
                    .into(imgAvatar)
            }
        }
        return rootView
    }
}