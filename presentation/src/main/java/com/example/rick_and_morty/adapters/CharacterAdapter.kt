package com.example.rick_and_morty.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rick_and_morty.R
import com.example.rick_and_morty.databinding.ListItemBinding
import com.example.domain.model.CharacterDomain

class CharacterAdapter : ListAdapter<CharacterDomain, CharacterAdapter.Holder>(Comparator()) {

    private var onItemClickListener: ((CharacterDomain) -> Unit)? = null
    private var onFavoriteClickListener: ((CharacterDomain) -> Unit)? = null
    class Holder(view: View, private val onFavoriteClickListener: ((CharacterDomain) -> Unit)?) : RecyclerView.ViewHolder(view) {
        private val binding = ListItemBinding.bind(view)
        fun bind(character: CharacterDomain) {
            with(binding) {
                nameChar.text = character.name
                if (!character.isLiked) {ibFav.setImageResource(R.drawable.ic_unliked)}
                else {ibFav.setImageResource(R.drawable.ic_liked)}
                Glide.with(root.context)
                    .load(character.image)
                    .into(imgChar)
                ibFav.setOnClickListener {
                    onFavoriteClickListener?.invoke(character)
                }
            }
        }
    }

    class Comparator : DiffUtil.ItemCallback<CharacterDomain>() {
        override fun areItemsTheSame(oldItem: CharacterDomain, newItem: CharacterDomain): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CharacterDomain, newItem: CharacterDomain): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return Holder(view, onFavoriteClickListener)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val character = getItem(position)
        holder.bind(character)
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(character)
        }
    }

    fun setOnItemClickListener(listener: (CharacterDomain) -> Unit) {
        this.onItemClickListener = listener
    }

    fun setOnFavoriteClickListener(listener: (CharacterDomain) -> Unit){
        this.onFavoriteClickListener = listener
    }
}
