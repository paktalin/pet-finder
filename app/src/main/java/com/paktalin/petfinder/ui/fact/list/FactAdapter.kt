package com.paktalin.petfinder.ui.fact.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paktalin.petfinder.databinding.FactListItemBinding
import com.paktalin.petfinder.model.Fact

class FactAdapter(
    private val onClick: (Fact) -> Unit
) : ListAdapter<Fact, FactViewHolder>(FactDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FactViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val onClick = { position: Int -> onClick(getItem(position)) }
        return FactViewHolder(
            view = FactListItemBinding.inflate(inflater, parent, false),
            onClick = onClick
        )
    }

    override fun onBindViewHolder(holder: FactViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class FactViewHolder(
    private val view: FactListItemBinding,
    private val onClick: (Int) -> Unit
) : RecyclerView.ViewHolder(view.root) {

    init {
        view.root.setOnClickListener { onClick(bindingAdapterPosition) }
    }

    fun bind(item: Fact) = with(view) {
        content.text = item.text
    }
}

private object FactDiffCallback : DiffUtil.ItemCallback<Fact>() {

    override fun areItemsTheSame(oldItem: Fact, newItem: Fact): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Fact, newItem: Fact): Boolean {
        return oldItem == newItem
    }
}
