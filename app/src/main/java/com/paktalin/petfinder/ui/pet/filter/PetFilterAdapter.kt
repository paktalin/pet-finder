package com.paktalin.petfinder.ui.pet.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paktalin.petfinder.databinding.PetFilterItemBinding
import com.paktalin.petfinder.model.PetType

class PetFilterAdapter(
    private val onClick: (name: String) -> Unit
) : ListAdapter<PetType, PetViewHolder>(PetDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val onClick = { position: Int -> onClick(getItem(position).name) }
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

    fun bind(item: PetType) = with(view) {
        name.text = item.name
    }
}

private object PetDiffCallback : DiffUtil.ItemCallback<PetType>() {

    override fun areItemsTheSame(oldItem: PetType, newItem: PetType): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: PetType, newItem: PetType): Boolean {
        return oldItem == newItem
    }
}
