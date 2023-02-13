package com.jfalstaff.tasteit.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jfalstaff.tasteit.databinding.FragmentRecipesBinding

class RecipesFragment : Fragment() {

    private var _binding: FragmentRecipesBinding? = null
    val binding get() = _binding ?: throw Exception("FragmentRecipesBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startShimmerEffect()
    }

    private fun startShimmerEffect() {
        binding.shimmerPlaceholderLayout.shimmerFrameLayout.startShimmer()
    }

    private fun stopShimmerEffect() {
        binding.shimmerPlaceholderLayout.shimmerFrameLayout.stopShimmer()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}