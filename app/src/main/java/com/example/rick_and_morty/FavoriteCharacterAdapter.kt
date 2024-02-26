package com.example.rick_and_morty

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rick_and_morty.databinding.FavoriteListItemBinding
import com.example.rick_and_morty.databinding.ListItemBinding
import com.example.rick_and_morty.model.Character

class FavoriteCharacterAdapter : ListAdapter<Character, FavoriteCharacterAdapter.Holder>(Comparator()) {

    class Holder(view: View) : RecyclerView.ViewHolder(view){
        private val binding = FavoriteListItemBinding.bind(view)

        fun bind(character: Character) = with(binding){
            nameChar.text = character.name
            speciesChar.text = character.species
            Glide.with(root.context)
                .load(character.image)
                .into(imgChar)
        }
    }

    class Comparator : DiffUtil.ItemCallback<Character>(){
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.favorite_list_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }
}
