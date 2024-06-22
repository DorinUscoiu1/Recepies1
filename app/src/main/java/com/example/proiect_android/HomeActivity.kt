package com.example.proiect_android

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.example.proiect_android.entites.*
import com.example.proiect_android.database.*


class HomeActivity : AppCompatActivity() {
    private lateinit var editTextRecipeTitle: EditText
    private lateinit var editTextRecipeIngredients: EditText
    private lateinit var editTextRecipeInstructions: EditText
    private lateinit var buttonAddRecipe: Button
    private lateinit var recyclerViewRecipes: RecyclerView
    private lateinit var recipeAdapter: RecipeAdapter
    private lateinit var recipeList: MutableList<Recipe>

    private lateinit var db: RecipeDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        // Initialize Recipe related views
        editTextRecipeTitle = findViewById(R.id.recipeTitle)
        editTextRecipeIngredients = findViewById(R.id.recipeIngredients)
        editTextRecipeInstructions = findViewById(R.id.recipeInstructions)
        buttonAddRecipe = findViewById(R.id.addRecipe)
        recyclerViewRecipes = findViewById(R.id.listOfRecipes)

        // Initialize Database
        db = Room.databaseBuilder(applicationContext, RecipeDatabase::class.java, "recipe-database").build()

        // Load Recipes
        GlobalScope.launch {
            recipeList = db.recipeDao().getAllRecipes().toMutableList()
            runOnUiThread {
                recyclerViewRecipes.layoutManager = LinearLayoutManager(this@HomeActivity)
                recipeAdapter = RecipeAdapter(recipeList) { recipe ->
                    GlobalScope.launch {
                        db.recipeDao().delete(recipe)
                        runOnUiThread {
                            recipeList.remove(recipe)
                            recipeAdapter.notifyDataSetChanged()
                        }
                    }
                }
                recyclerViewRecipes.adapter = recipeAdapter
            }
        }

        // Add Recipe
        buttonAddRecipe.setOnClickListener {
            val title = editTextRecipeTitle.text.toString().trim()
            val ingredients = editTextRecipeIngredients.text.toString().trim()
            val instructions = editTextRecipeInstructions.text.toString().trim()

            if (title.isEmpty() || ingredients.isEmpty() || instructions.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            GlobalScope.launch {
                var recipe = recipeList.find { it.title.equals(title, ignoreCase = true) }
                if (recipe != null) {
                    recipe.ingredients = ingredients
                    recipe.instructions = instructions
                    db.recipeDao().update(recipe)
                } else {
                    recipe = Recipe(title = title, ingredients = ingredients, instructions = instructions)
                    db.recipeDao().insert(recipe)
                    recipeList.add(recipe)
                }

                runOnUiThread {
                    recipeAdapter.notifyDataSetChanged()
                }
            }
        }
    }
}
