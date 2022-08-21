package com.peter.asteroids.framework.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.peter.asteroids.R
import com.peter.asteroids.business.models.Asteroid
import com.peter.asteroids.databinding.RowAsteroidBinding

class AsteroidsAdapter(private val onClickListener: OnAsteroidClickListener) :
    ListAdapter<Asteroid, AsteroidResultViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidResultViewHolder {
        val binding = DataBindingUtil.inflate<RowAsteroidBinding>(
            LayoutInflater.from(parent.context), R.layout.row_asteroid, parent, false
        )

        return AsteroidResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AsteroidResultViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }

        holder.itemView.setOnClickListener(View.OnClickListener {
            getItem(position)?.let { it1 -> onClickListener.onClick(it1) }
        })

    }

    companion object DiffCallback : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id
        }
    }
}

class AsteroidResultViewHolder(private var binding: RowAsteroidBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(Item: Asteroid) {
        binding.data = Item
        binding.executePendingBindings()
    }
}

class OnAsteroidClickListener(val clickListener: (item: Asteroid) -> Unit) {
    fun onClick(item: Asteroid) = clickListener(item)
}