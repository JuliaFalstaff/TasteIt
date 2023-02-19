package com.jfalstaff.tasteit.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jfalstaff.tasteit.databinding.FragmentRecipesBinding
import com.jfalstaff.tasteit.domain.NetworkResult
import com.jfalstaff.tasteit.presentation.adapters.RecipesAdapter
import com.jfalstaff.tasteit.presentation.util.observeOnce
import com.jfalstaff.tasteit.presentation.viewmodels.MainViewModel
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
        readDataBase()
    }

    private fun readDataBase() {
        Log.d("VVV", "Database data")
        viewModel.readRecipes.observeOnce(viewLifecycleOwner) { database ->
            if (database.isNotEmpty()) {
                adapter.setData(database.first())
                stopShimmerEffect()
            } else {
                requestApiData()
            }
        }
    }

    private fun setUpRV() {
        binding.recipesRecyclerView.adapter = adapter
    }

    private fun loadCacheData() {
        viewModel.readRecipes.observe(viewLifecycleOwner) {
            adapter.setData(it.first())
        }
    }

    private fun requestApiData() {
        Log.d("VVV", "Api data")
        viewModel.getRecipes()
        viewModel.recipes.observe(viewLifecycleOwner) { response ->
            when (response) {
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
                    loadCacheData()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
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