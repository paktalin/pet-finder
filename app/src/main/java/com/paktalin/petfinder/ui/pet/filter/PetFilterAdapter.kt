package com.paktalin.petfinder.ui.pet.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paktalin.petfinder.databinding.PetFilterItemBinding

class PetFilterAdapter(
    private val onClick: (name: String) -> Unit
) : ListAdapter<String, PetViewHolder>(PetDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val onClick = { position: Int -> onClick(getItem(position)) }
        return PetViewHolder(
            view = PetFilterItemBinding.inflate(inflater, parent, false),
            onClick = onClick
        )
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PetViewHolder(
    private val view: PetFilterItemBinding,
    private val onClick: (Int) -> Unit
) : RecyclerView.ViewHolder(view.root) {

    init {
        view.root.setOnClickListener { onClick(bindingAdapterPosition) }
    }

    fun bind(petType: String) = with(view) {
        name.text = petType
    }
}

private object PetDiffCallback : DiffUtil.ItemCallback<String>() {

    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
}
