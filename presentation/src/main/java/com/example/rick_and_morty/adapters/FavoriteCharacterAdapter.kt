package com.example.rick_and_morty.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.CharacterDomain
import com.example.rick_and_morty.R
import com.example.rick_and_morty.databinding.FavoriteListItemBinding

class FavoriteCharacterAdapter : ListAdapter<CharacterDomain, FavoriteCharacterAdapter.Holder>(Comparator()) {

    class Holder(view: View) : RecyclerView.ViewHolder(view){
        private val binding = FavoriteListItemBinding.bind(view)

        fun bind(character: CharacterDomain) = with(binding){
            nameChar.text = character.name
            speciesChar.text = character.species
            Glide.with(root.context)
                .load(character.image)
                .into(imgChar)
        }
    }

    class Comparator : DiffUtil.ItemCallback<CharacterDomain>(){
        override fun areItemsTheSame(oldItem: CharacterDomain, newItem: CharacterDomain): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CharacterDomain, newItem: CharacterDomain): Boolean {
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
