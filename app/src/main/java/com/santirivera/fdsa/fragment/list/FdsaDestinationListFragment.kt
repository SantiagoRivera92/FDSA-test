package com.santirivera.fdsa.fragment.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.santirivera.domain.model.DestinationItem
import dagger.hilt.android.AndroidEntryPoint
import com.santirivera.fdsa.R
import com.santirivera.fdsa.fragment.list.adapter.viewholder.FdsaDestinationViewHolder
import com.santirivera.fdsa.fragment.base.FdsaDestinationBaseFragment
import com.santirivera.fdsa.databinding.FragmentItemListBinding
import com.santirivera.fdsa.fragment.list.adapter.FdsaDestinationListAdapter
import java.util.*

@AndroidEntryPoint
class FdsaDestinationListFragment :
    FdsaDestinationBaseFragment(),
    FdsaDestinationViewHolder.OnDestinationClickedCallback,
    MenuProvider {

    private var _binding: FragmentItemListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FdsaDestinationListViewModel by viewModels()
    private val adapter = FdsaDestinationListAdapter(ArrayList(), this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        val recyclerView: RecyclerView = binding.itemList
        setupRecyclerView(recyclerView)
        startLoad()
        viewModel.loadDestinations()
        observeViewModel()
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = adapter
        binding.itemList.layoutManager = LinearLayoutManager(context)
    }

    private fun observeViewModel() {
        viewModel.filteredDestinations.observe(viewLifecycleOwner) {
            adapter.setNewValues(it)
            adapter.notifyDataSetChanged()
            endLoad()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestinationClicked(item: DestinationItem) {
        val action = FdsaDestinationListFragmentDirections
            .actionItemListFragmentToFdsaDestinationDetailFragment(item)
        findNavController().navigate(action)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.list, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.action_search -> {
                val searchView = menuItem.actionView as? SearchView
                searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        query?.let { viewModel.filter(it) }
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        newText?.let { viewModel.filter(it) }
                        return true
                    }
                })
                return true
            }
            R.id.action_add_destination -> {
                val action = FdsaDestinationListFragmentDirections.actionItemListFragmentToFdsaDestinationCreateFragment()
                findNavController().navigate(action)
                return true
            }
        }
        return false
    }
}
