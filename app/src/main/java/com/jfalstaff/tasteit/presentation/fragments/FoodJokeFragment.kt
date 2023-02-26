package com.jfalstaff.tasteit.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jfalstaff.tasteit.databinding.FragmentFoodJokeBinding

class FoodJokeFragment : Fragment() {

    private var _binding: FragmentFoodJokeBinding? = null
    val binding get() = _binding ?: throw Exception("FragmentRecipesBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoodJokeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}