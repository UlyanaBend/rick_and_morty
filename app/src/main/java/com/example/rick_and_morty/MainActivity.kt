package com.example.rick_and_morty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.rick_and_morty.model.Character
import com.example.rick_and_morty.model.CharacterAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvModel = findViewById<TextView>(R.id.tvModel)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val characterAPI = retrofit.create(CharacterAPI::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val character = characterAPI.getCharacterById(2)
            runOnUiThread {
                tvModel.text = character.name
            }
        }
    }
}