package com.example.rick_and_morty

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rick_and_morty.databinding.ListItemBinding
import com.example.rick_and_morty.model.Character

class CharacterAdapter(private val characterList: List<Character>, private val listener: CharacterItemClickListener) : ListAdapter<Character, CharacterAdapter.Holder>(Comparator()) {

    class Holder(view: View, private val listener: CharacterItemClickListener, private val characterList: List<Character>) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private val binding = ListItemBinding.bind(view)

        override fun onClick(v: View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val character = characterList[position]
                listener.onItemClick(character)
            }
        }

        fun bind(character: Character) {
            with(binding) {
                nameChar.text = character.name
                Glide.with(root.context)
                    .load(character.image)
                    .into(imgChar)
            }
        }

        init {
            itemView.setOnClickListener(this)
        }
    }

    class Comparator : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return Holder(view, listener, characterList)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }
}
