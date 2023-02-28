package com.jfalstaff.tasteit.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.jfalstaff.tasteit.databinding.RecipesBottomSheetBinding
import com.jfalstaff.tasteit.presentation.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipesBottomSheet : BottomSheetDialogFragment() {

    private var _binding: RecipesBottomSheetBinding? = null
    val binding get() = _binding ?: throw Exception("RecipesBottomSheetBinding is null")
    private val viewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }
    private var mealTypeId: Int = 0
    private var mealType: String = ""
    private var dietTypeId: Int = 0
    private var dietType: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RecipesBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        readDataFromStore()
        chipsListener()
        saveChips()
    }

    private fun readDataFromStore() {
        viewModel.readDataStore.asLiveData().observe(viewLifecycleOwner) {
            dietType = it.selectedDietType
            mealType = it.selectedMealType
            updateChips(it.selectedDietTypeId, binding.dietTypeChipGroup)
            updateChips(it.selectedMealTypeId, binding.mealTypeChipGroup)
        }
    }

    private fun updateChips(chipId: Int, chipGroup: ChipGroup) {
        if (chipId != 0) {
            chipGroup.findViewById<Chip>(chipId).isChecked = true
        }
    }

    private fun chipsListener() {
        binding.mealTypeChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            val chip: Chip? = group.findViewById(group.checkedChipId)
            val selectedMealType = chip?.text.toString()
            mealTypeId = group.checkedChipId
            mealType = selectedMealType
        }

        binding.dietTypeChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            val chip: Chip? = group.findViewById(group.checkedChipId)
            val selectedDietType = chip?.text.toString()
            dietTypeId = group.checkedChipId
            dietType = selectedDietType
        }
    }

    private fun saveChips() {
        binding.applyButton.setOnClickListener {
            viewModel.saveDietAndMealType(mealType, mealTypeId, dietType, dietTypeId)
            val action =
                RecipesBottomSheetDirections.actionRecipesBottomSheetToRecipesFragment(true)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}