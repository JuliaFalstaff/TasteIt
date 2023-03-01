package com.jfalstaff.tasteit.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jfalstaff.tasteit.R
import com.jfalstaff.tasteit.databinding.FragmentRecipesBinding
import com.jfalstaff.tasteit.domain.NetworkResult
import com.jfalstaff.tasteit.presentation.adapters.RecipesAdapter
import com.jfalstaff.tasteit.presentation.util.NetworkListener
import com.jfalstaff.tasteit.presentation.util.hasInternetConnection
import com.jfalstaff.tasteit.presentation.util.observeOnce
import com.jfalstaff.tasteit.presentation.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private var _binding: FragmentRecipesBinding? = null
    val binding get() = _binding ?: throw Exception("FragmentRecipesBinding is null")
    private val adapter: RecipesAdapter by lazy { RecipesAdapter() }
    private val viewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }
    val args by navArgs<RecipesFragmentArgs>()
    private lateinit var networkListener: NetworkListener

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
        setFabClickListener()
        observeNetworkState()

    }

    private fun observeNetworkState() {
        networkListener = NetworkListener()
        lifecycleScope.launch {
            networkListener.checkNetworkAvailability(requireContext())
                .collect {
                    Log.d("Network VVV", it.toString())
                    viewModel.setNetworkStatus(it)
                    showNetworkStatus(it)
                }
        }
    }

    private fun showNetworkStatus(isOnline: Boolean) {
        if (!isOnline) {
            Toast.makeText(requireContext(), "No Internet connection", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setFabClickListener() {
        binding.recipesFab.setOnClickListener {
                if(viewModel.networkStatus.value == true) {
                    findNavController().navigate(R.id.action_recipesFragment_to_recipesBottomSheet)
                } else {
                    showNetworkStatus(false)
                }
        }
    }

    private fun readDataBase() {
        if (hasInternetConnection(requireActivity())) {
            viewModel.readRecipesFromDB.observeOnce(viewLifecycleOwner) { db ->
                if (db.isNotEmpty() && !args.backFromBottomFragment) {
                    Log.d("VVV", "DB data")
                    adapter.setData(db.first())
                    stopShimmerEffect()
                } else {
                    requestApiData()
                }
            }
        } else {
            loadCacheData()
        }
    }

    private fun setUpRV() {
        binding.recipesRecyclerView.adapter = adapter
    }

    private fun loadCacheData() {
        stopShimmerEffect()
        viewModel.readRecipesFromDB.observe(viewLifecycleOwner) { db ->
            if (db.isNotEmpty()) {
                adapter.setData(db.first())
            } else {
                binding.errorImageView.visibility = View.VISIBLE
                binding.errorTextView.visibility = View.VISIBLE
            }
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
                    binding.errorImageView.visibility = View.INVISIBLE
                    binding.errorTextView.visibility = View.INVISIBLE
                }
                is NetworkResult.Loading -> {
                    startShimmerEffect()
                    binding.errorImageView.visibility = View.INVISIBLE
                    binding.errorTextView.visibility = View.INVISIBLE
                }
                is NetworkResult.Error -> {
                    stopShimmerEffect()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    loadCacheData()
                    binding.errorImageView.visibility = View.VISIBLE
                    binding.errorTextView.visibility = View.VISIBLE
                    binding.errorTextView.text = response.message.toString()
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