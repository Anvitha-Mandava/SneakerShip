package com.example.sneakership.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakership.databinding.ItemCartDetailBinding
import com.example.sneakership.models.SneakersCart
import com.example.sneakership.viewModels.CartDetailViewModel
import com.example.sneakership.viewModels.SneakerItemViewModel

class CartDetailAdapter(val viewModel: CartDetailViewModel) :
    ListAdapter<SneakersCart, CartDetailAdapter.CartViewHolder>(CartDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding =
            ItemCartDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CartViewHolder(private val binding: ItemCartDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(sneaker: SneakersCart) {
            binding.apply {
                itemViewModel = SneakerItemViewModel(sneaker.name, sneaker.image, sneaker.price)
                removeFromCart.setOnClickListener {
                    viewModel.removeSneakerFromCart(sneaker)
                }
            }
        }
    }

    private class CartDiffCallback : DiffUtil.ItemCallback<SneakersCart>() {
        override fun areItemsTheSame(oldItem: SneakersCart, newItem: SneakersCart): Boolean {
            return false
        }

        override fun areContentsTheSame(oldItem: SneakersCart, newItem: SneakersCart): Boolean {
            return oldItem == newItem
        }
    }
}

