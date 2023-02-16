package com.jfalstaff.tasteit.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jfalstaff.tasteit.BuildConfig
import com.jfalstaff.tasteit.databinding.FragmentRecipesBinding
import com.jfalstaff.tasteit.domain.NetworkResult
import com.jfalstaff.tasteit.presentation.adapters.RecipesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private var _binding: FragmentRecipesBinding? = null
    val binding get() = _binding ?: throw Exception("FragmentRecipesBinding is null")
    private val adapter: RecipesAdapter by lazy { RecipesAdapter() }
    private val viewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }

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
        setUpRV()
    }

    private fun setUpRV() {
        binding.recipesRecyclerView.adapter = adapter
        viewModel.getRecipes(applyQueries())
        viewModel.recipes.observe(viewLifecycleOwner) { response ->
            when(response) {
                is NetworkResult.Success -> {
                    stopShimmerEffect()
                    response.data?.let { recipe ->
                        adapter.setData(recipe)
                    }
                }
                is NetworkResult.Loading -> {
                    startShimmerEffect()
                }
                is NetworkResult.Error -> {
                    stopShimmerEffect()
                    Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun applyQueries() : HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries["number"] = "50"
        queries["apiKey"] = BuildConfig.API_KEY
        queries["type"] = "snack"
        queries["diet"] = "vegan"
        queries["addRecipeInformation"] = "true"
        queries["fillIngredients"] = "true"
        return queries
    }

    private fun startShimmerEffect() {
        binding.shimmerPlaceholderLayout.shimmerFrameLayout.startShimmer()
        binding.shimmerPlaceholderLayout.shimmerFrameLayout.visibility = View.VISIBLE
    }

    private fun stopShimmerEffect() {
        binding.shimmerPlaceholderLayout.shimmerFrameLayout.stopShimmer()
        binding.shimmerPlaceholderLayout.shimmerFrameLayout.visibility = View.INVISIBLE
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}