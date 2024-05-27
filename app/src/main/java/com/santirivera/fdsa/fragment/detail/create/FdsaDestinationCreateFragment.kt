package com.santirivera.fdsa.fragment.detail.create

import FlagEmojiAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.santirivera.domain.model.DestinationItem
import com.santirivera.fdsa.R
import com.santirivera.fdsa.databinding.FragmentItemCreateBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FdsaDestinationCreateFragment : Fragment(), MenuProvider {

    private var _binding: FragmentItemCreateBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FdsaDestinationCreateViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemCreateBinding.inflate(inflater, container, false)

        requireActivity().addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.creationResult.observe(viewLifecycleOwner) { creationSuccess: Boolean ->
            if (creationSuccess) {
                findNavController().navigateUp()
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage: String ->
            Snackbar.make(binding.buttonSave, errorMessage, Snackbar.LENGTH_SHORT).show()
        }

        setupCountryCodeSpinner(binding.spinnerCountryCode)
        setupDestinationTypeSpinner(binding.spinnerType)

        binding.buttonSave.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val description = binding.editTextDescription.text.toString()
            val countryCode = binding.spinnerCountryCode.selectedItem as String
            val type = binding.spinnerType.selectedItem as String

            // Check if all fields are filled
            if (name.isNotEmpty() && description.isNotEmpty() && countryCode.isNotEmpty() && type.isNotEmpty()) {
                val newItem = DestinationItem(
                    id = 0,
                    name = name,
                    description = description,
                    countryCode = countryCode,
                    destinationType = type
                )
                viewModel.createDestination(newItem)
            } else {
                Snackbar.make(it, R.string.fill_all_fields, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupCountryCodeSpinner(spinner: Spinner) {
        val countryCodes = resources.getStringArray(R.array.country_codes).toList()
        val adapter = FlagEmojiAdapter(requireContext(), countryCodes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    private fun setupDestinationTypeSpinner(spinner: Spinner) {
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.destination_types,
            R.layout.destination_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        // Do nothing: We're only trying to get the Up button working
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            android.R.id.home -> {
                // Handle the back button click event
                    findNavController().navigateUp()
            }
        }
        return false
    }
}
