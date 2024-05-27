package com.santirivera.fdsa.fragment.detail.update

import FlagEmojiAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.santirivera.domain.model.DestinationItem
import com.santirivera.fdsa.R
import com.santirivera.fdsa.databinding.FragmentItemDetailBinding
import com.santirivera.fdsa.fragment.base.FdsaDestinationBaseFragment
import com.santirivera.fdsa.utils.toFlagEmoji
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FdsaDestinationDetailFragment : FdsaDestinationBaseFragment(), MenuProvider {

    private var _binding: FragmentItemDetailBinding? = null
    private val binding get() = _binding!!

    private var editModeActive = false

    private val viewModel: FdsaDestinationDetailViewModel by viewModels()

    private lateinit var destinationItem: DestinationItem
    private lateinit var countryCodeAdapter: FlagEmojiAdapter
    private lateinit var destinationTypeAdapter: ArrayAdapter<CharSequence>

    private var menu: Menu? = null // Reference to the Menu object

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Retrieve the arguments
        val args: FdsaDestinationDetailFragmentArgs by navArgs()
        destinationItem = args.destinationItem

        requireActivity().addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Inflate the layout for this fragment
        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.updateDestinationResult.observe(viewLifecycleOwner) { success: Boolean ->
            if (success) {
                Snackbar.make(binding.root, R.string.destination_updated, Snackbar.LENGTH_SHORT)
                    .show()
                findNavController().navigateUp()
            }
        }
        viewModel.deleteDestinationResult.observe(viewLifecycleOwner) { success: Boolean ->
            if (success) {
                Snackbar.make(binding.root, R.string.destination_deleted, Snackbar.LENGTH_SHORT)
                    .show()
                findNavController().navigateUp()
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage: String ->
            Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_SHORT).show()
        }


        // Setup country code spinner
        val countryCodes = resources.getStringArray(R.array.country_codes).toList()
        countryCodeAdapter = FlagEmojiAdapter(
            requireContext(),
            countryCodes
        )

        countryCodeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.destinationDetailSpinnerCountryCode.adapter = countryCodeAdapter

        // Setup destination type spinner
        destinationTypeAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.destination_types,
            R.layout.destination_spinner_item
        )
        destinationTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.destinationDetailSpinnerType.adapter = destinationTypeAdapter

        // Set listener for country code spinner
        binding.destinationDetailSpinnerCountryCode.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (editModeActive) {
                    val selectedCountryCode =
                        countryCodeAdapter.getItem(position).toString()
                    binding.destinationDetailTextCountryCode.text = selectedCountryCode
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }

        // Set listener for destination type spinner
        binding.destinationDetailSpinnerType.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (editModeActive) {
                    val selectedDestinationType =
                        destinationTypeAdapter.getItem(position).toString()
                    binding.destinationDetailTextType.text = selectedDestinationType
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }

        // Set the onClicks for both buttons

        binding.destinationDetailButtonSave.setOnClickListener {
            val name = binding.destinationDetailEditName.text.toString()
            val description = binding.destinationDetailEditDescription.text.toString()
            val countryCode = binding.destinationDetailSpinnerCountryCode.selectedItem as String
            val type = binding.destinationDetailSpinnerType.selectedItem as String
            val newItem = DestinationItem(destinationItem.id, name, description, countryCode, type, System.currentTimeMillis())
            viewModel.updateDestination(newItem)
            destinationItem = newItem
            toggleEditMode()
        }

        binding.destinationDetailButtonCancel.setOnClickListener {
            toggleEditMode()
        }

        // Update visibility based on edit mode
        updateVisibility()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.detail, menu)
        this.menu = menu // Save reference to the Menu object
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            android.R.id.home -> {
                // Handle the back button click event
                return if (editModeActive) {
                    toggleEditMode()
                    true
                } else {
                    findNavController().navigateUp()
                }
            }
            R.id.action_delete_destination -> {
                viewModel.deleteDestination(destinationItem)
                return true
            }
            R.id.action_edit_destination -> {
                toggleEditMode()
                return true
            }
        }
        return false
    }

    private fun toggleEditMode() {
        editModeActive = !editModeActive
        updateVisibility()
        updateMenuVisibility() // Update menu item visibility
    }

    private fun updateVisibility() {
        if (editModeActive) {
            binding.viewModeGroup.visibility = View.GONE
            binding.editModeGroup.visibility = View.VISIBLE
        } else {
            binding.viewModeGroup.visibility = View.VISIBLE
            binding.editModeGroup.visibility = View.GONE
        }

        binding.destinationDetailTextName.text = destinationItem.name
        binding.destinationDetailTextDescription.text = destinationItem.description
        binding.destinationDetailTextCountryCode.text = destinationItem.countryCode.toFlagEmoji()
        binding.destinationDetailTextType.text = destinationItem.destinationType

        binding.destinationDetailEditName.setText(destinationItem.name)
        binding.destinationDetailEditDescription.setText(destinationItem.description)

        val countryCodePosition = countryCodeAdapter.getPosition(destinationItem.countryCode)
        binding.destinationDetailSpinnerCountryCode.setSelection(countryCodePosition)
        val destinationTypePosition = destinationTypeAdapter.getPosition(destinationItem.destinationType)
        binding.destinationDetailSpinnerType.setSelection(destinationTypePosition)
    }

    private fun updateMenuVisibility() {
        menu?.findItem(R.id.action_edit_destination)?.isVisible = !editModeActive
        menu?.findItem(R.id.action_delete_destination)?.isVisible = !editModeActive
    }
}
