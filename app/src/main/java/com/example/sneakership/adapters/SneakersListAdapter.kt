package com.example.sneakership.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakership.databinding.ItemSneakerListBinding
import com.example.sneakership.models.Sneaker
import com.example.sneakership.models.SneakersCart
import com.example.sneakership.viewModels.SneakerItemViewModel
import com.example.sneakership.viewModels.SneakersListViewModel

class SneakersListAdapter(
    val viewModel: SneakersListViewModel, private val onSneakerItemClick: (sneaker: Sneaker) -> Unit
) : ListAdapter<Sneaker, SneakersListAdapter.SneakerViewHolder>(SneakerDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SneakerViewHolder {
        val binding =
            ItemSneakerListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SneakerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SneakerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SneakerViewHolder(private val binding: ItemSneakerListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(sneaker: Sneaker) {
            binding.apply {
                itemViewModel =
                    SneakerItemViewModel(sneaker.name, sneaker.media.imageUrl, sneaker.retailPrice)
                addToCart.setOnClickListener {
                    viewModel.addSneakerToCart(
                        SneakersCart(
                            name = sneaker.name,
                            image = sneaker.media.imageUrl,
                            price = sneaker.retailPrice
                        )
                    )
                }
                cardView.setOnClickListener {
                    onSneakerItemClick(sneaker)
                }
            }
        }
    }

    private class SneakerDiffCallback : DiffUtil.ItemCallback<Sneaker>() {
        override fun areItemsTheSame(oldItem: Sneaker, newItem: Sneaker): Boolean {
            return false
        }

        override fun areContentsTheSame(oldItem: Sneaker, newItem: Sneaker): Boolean {
            return oldItem == newItem
        }
    }
}
