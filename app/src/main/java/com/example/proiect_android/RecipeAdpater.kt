package com.example.proiect_android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proiect_android.entites.Recipe

class RecipeAdapter(
    private var recipes: List<Recipe>,
    private val onClick: (Recipe) -> Unit,
    private val onDelete: (Recipe) -> Unit
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.recipeTitle)
        val ingredients: TextView = itemView.findViewById(R.id.recipeIngredients)
        val instructions: TextView = itemView.findViewById(R.id.recipeInstructions)
        val deleteItem: ImageButton = itemView.findViewById(R.id.deleteItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recipe_item, parent, false)
        return RecipeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val currentRecipe = recipes[position]
        holder.title.text = currentRecipe.DishName
        holder.ingredients.text = currentRecipe.Ingredients
        holder.instructions.text = currentRecipe.Instructions
        holder.itemView.setOnClickListener {
            onClick(currentRecipe)
        }
        holder.deleteItem.setOnClickListener {
            onDelete(currentRecipe)
        }
    }

    override fun getItemCount() = recipes.size

    fun setData(newRecipes: List<Recipe>) {
        recipes = newRecipes
        notifyDataSetChanged()
    }
}
