package com.santirivera.fdsa.fragment.list.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.santirivera.domain.model.DestinationItem
import com.santirivera.fdsa.databinding.ItemListContentBinding
import com.santirivera.fdsa.utils.toFlagEmoji

class FdsaDestinationViewHolder(private val binding: ItemListContentBinding) :
    RecyclerView.ViewHolder(binding.root) {

    interface OnDestinationClickedCallback {
        fun onDestinationClicked(item: DestinationItem)
    }

    fun setElement(
        item: DestinationItem, callback: OnDestinationClickedCallback
    ) {
        binding.textViewDestinationName.text = item.name
        binding.textViewDestinationDescription.text = item.description
        binding.textViewCountryCode.text = item.countryCode.toFlagEmoji()
        itemView.setOnClickListener { callback.onDestinationClicked(item) }
    }
}

