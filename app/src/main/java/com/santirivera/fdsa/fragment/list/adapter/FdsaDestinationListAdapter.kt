package com.santirivera.fdsa.fragment.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.santirivera.domain.model.DestinationItem
import com.santirivera.fdsa.databinding.ItemListContentBinding
import com.santirivera.fdsa.fragment.list.adapter.viewholder.FdsaDestinationViewHolder
import java.util.*

class FdsaDestinationListAdapter(
    private val values: ArrayList<DestinationItem>,
    private val callback: FdsaDestinationViewHolder.OnDestinationClickedCallback) :
    RecyclerView.Adapter<FdsaDestinationViewHolder>() {

    private var filter = ""
    private var filteredValues = ArrayList<DestinationItem>()

    init {
        for (value in values){
            filteredValues.add(value)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FdsaDestinationViewHolder {
        val binding = ItemListContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FdsaDestinationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FdsaDestinationViewHolder, position: Int) {
        val item = filteredValues[position]
        holder.setElement(item, callback)
    }

    override fun getItemCount():Int {
        return filteredValues.size
    }

    fun filter(filter: String){
        this.filter = filter
        filteredValues.clear()
        for (value in values){
            if (value.name.lowercase().contains(filter.lowercase())){
                filteredValues.add(value)
            }
        }
        notifyDataSetChanged()
    }

    fun setNewValues(it: List<DestinationItem>) {
        values.clear()
        values.addAll(it)
        filter(filter)
    }
}