package com.jfalstaff.tasteit.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.jfalstaff.tasteit.R
import com.jfalstaff.tasteit.databinding.ItemRecepieLayoutBinding
import com.jfalstaff.tasteit.domain.entities.FoodRecipe
import com.jfalstaff.tasteit.domain.entities.Result

class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder>() {

    private var recipesList = emptyList<Result>()

    fun setData(newData: FoodRecipe) {
        val diffUtilCallback = RecipesDiffUtil(emptyList(), newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtilCallback)
        recipesList = newData.results
        diffUtilResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        return RecipesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        holder.bind(recipesList[position])
    }

    override fun getItemCount(): Int = recipesList.size

    class RecipesViewHolder(private val binding: ItemRecepieLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Result) = with(binding) {
            titleTextView.text = result.title
            descriptionTextView.text = result.summary
            likeTextView.text = result.aggregateLikes.toString()
            timeTextView.text = result.readyInMinutes.toString()
            recipeImageView.load(result.image) {
                crossfade(600)
                error(R.drawable.ic_error_placeholder)
            }
//            Glide.with(itemView).load(result.image).into(recipeImageView)
            if (result.vegan) {
                veganTextView.setTextColor(ContextCompat.getColor(itemView.context, R.color.green))
                veganImageView.setColorFilter(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.green
                    )
                )
            }

        }

        companion object {
            fun from(parent: ViewGroup): RecipesViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemRecepieLayoutBinding.inflate(layoutInflater, parent, false)
                return RecipesViewHolder(binding)
            }
        }
    }
}