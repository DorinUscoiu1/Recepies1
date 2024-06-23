package com.example.proiect_android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.proiect_android.databinding.ActivityRecipeDetailBinding
import com.example.proiect_android.entites.Recipe

class RecipeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recipe = intent.getSerializableExtra("RECIPE_DETAIL") as Recipe

        binding.tvDishName.text = recipe.DishName
        binding.tvCategory.text = recipe.Category
        binding.tvIngredients.text = recipe.Ingredients
        binding.tvInstructions.text = recipe.Instructions
    }
}
