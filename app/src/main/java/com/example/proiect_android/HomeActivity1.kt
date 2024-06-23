package com.example.proiect_android

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.proiect_android.adapter.MainCategoryAdapter
import com.example.proiect_android.adapter.SubCategoryAdapter
import com.example.proiect_android.database.RecipeDatabase
import com.example.proiect_android.databinding.ActivityHome1Binding
import com.example.proiect_android.entites.Category
import com.example.proiect_android.entites.Recipe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class HomeActivity1 : AppCompatActivity() {

    private lateinit var binding: ActivityHome1Binding
    private var arrMainCategory = ArrayList<Recipe>()
    private var arrSubCategory = ArrayList<Category>()
    private var arrCategories = ArrayList<String>()
    private lateinit var mainCategoryAdapter: MainCategoryAdapter
    private lateinit var subCategoryAdapter: SubCategoryAdapter

    private var currentQuery: String = ""
    private var currentCategory: String = "All Categories"

    private lateinit var recipeDatabase: RecipeDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHome1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        recipeDatabase = Room.databaseBuilder(applicationContext, RecipeDatabase::class.java, "recipe.db").build()

        mainCategoryAdapter = MainCategoryAdapter { recipe ->
            val intent = Intent(this, RecipeDetailActivity::class.java)
            intent.putExtra("RECIPE_DETAIL", recipe)
            startActivity(intent)
        }

        subCategoryAdapter = SubCategoryAdapter { category ->
            currentCategory = category.categoryName
            filterRecipes()
        }
        binding.itemShowHome.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.itemShowHome.adapter = mainCategoryAdapter

        binding.subitemShowHome.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.subitemShowHome.adapter = subCategoryAdapter

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    currentQuery = it
                    filterRecipes()
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    currentQuery = it
                    filterRecipes()
                }
                return false
            }
        })

        binding.buttonShowRecipes.setOnClickListener {
            supportFragmentManager.commit {
                replace(R.id.fragment_container, RecipeListFragment())
                addToBackStack(null)
            }
        }

        val url = "https://www.themealdb.com/api/json/v1/1/search.php?s="
        AsyncTaskHandleJson().execute(url)
        loadRecipesFromDatabase()
    }

    inner class AsyncTaskHandleJson : AsyncTask<String, String, String>() {
        override fun doInBackground(vararg url: String?): String {
            var text: String
            val connection = URL(url[0]).openConnection() as HttpURLConnection
            try {
                connection.connect()
                text = connection.inputStream.use { it.reader().use { reader -> reader.readText() } }
            } finally {
                connection.disconnect()
            }
            return text
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            handleJson(result)
        }
    }

    private fun handleJson(jsonString: String?) {
        val jsonObject = JSONObject(jsonString)
        val jsonArray = jsonObject.getJSONArray("meals")

        val recipeList = ArrayList<Recipe>()
        val categoryList = ArrayList<Category>()
        val categories = HashSet<String>()

        for (i in 0 until jsonArray.length()) {
            val mealObject = jsonArray.getJSONObject(i)

            val id = mealObject.getString("idMeal").toInt()
            val name = mealObject.getString("strMeal")
            val category = mealObject.getString("strCategory")
            val instructions = mealObject.getString("strInstructions")
            val ingredients = (1..20).joinToString("\n") {
                val ingredient = mealObject.getString("strIngredient$it")
                val measure = mealObject.getString("strMeasure$it")
                if (ingredient.isNotBlank()) "$measure $ingredient" else ""
            }

            categories.add(category)
            recipeList.add(Recipe(id, name, category, instructions, ingredients))

            categoryList.add(Category(category))
        }

        arrMainCategory.clear()
        arrMainCategory.addAll(recipeList)

        arrSubCategory.clear()
        categoryList.add(Category("All Categories"))
        arrSubCategory.addAll(categoryList.toHashSet())

        arrCategories.clear()
        arrCategories.addAll(categories)

        mainCategoryAdapter.setData(arrMainCategory)
        subCategoryAdapter.setData(arrSubCategory)

        saveRecipesToDatabase(recipeList)
    }

    private fun filterRecipes() {
        val filteredMainCategory = arrMainCategory.filter {
            it.DishName.contains(currentQuery, ignoreCase = true) &&
                    (currentCategory == "All Categories" || it.Category.equals(currentCategory, ignoreCase = true))
        }
        mainCategoryAdapter.setData(filteredMainCategory)
    }

    private fun saveRecipesToDatabase(recipes: List<Recipe>) {
        CoroutineScope(Dispatchers.IO).launch {
            recipeDatabase.recipeDao().insert(recipes)
        }
    }

    private fun loadRecipesFromDatabase() {
        CoroutineScope(Dispatchers.IO).launch {
            val recipes = recipeDatabase.recipeDao().getAllRecipes()
            arrMainCategory.clear()
            arrMainCategory.addAll(recipes)
            runOnUiThread {
                mainCategoryAdapter.setData(arrMainCategory)
            }
        }
    }
}
