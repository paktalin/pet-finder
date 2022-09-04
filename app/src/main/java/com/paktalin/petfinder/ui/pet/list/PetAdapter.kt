package com.paktalin.petfinder.ui.pet.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paktalin.petfinder.databinding.PetListItemBinding
import com.paktalin.petfinder.model.Pet
import com.paktalin.petfinder.ui.pet.loadPetPicture

class PetAdapter(
    private val onClick: (id: Long) -> Unit
) : ListAdapter<Pet, PetViewHolder>(PetDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val onClick = { position: Int -> onClick(getItem(position).id) }
        return PetViewHolder(
            view = PetListItemBinding.inflate(inflater, parent, false),
            onClick = onClick
        )
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PetViewHolder(
    private val view: PetListItemBinding,
    private val onClick: (Int) -> Unit
) : RecyclerView.ViewHolder(view.root) {

    init {
        view.cardView.setOnClickListener { onClick(bindingAdapterPosition) }
    }

    fun bind(item: Pet) = with(view) {
        name.text = item.name
        description.text = item.description
        pictureImage.loadPetPicture(item.pictureUrl)
    }
}

private object PetDiffCallback : DiffUtil.ItemCallback<Pet>() {

    override fun areItemsTheSame(oldItem: Pet, newItem: Pet): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Pet, newItem: Pet): Boolean {
        return oldItem == newItem
    }
}
