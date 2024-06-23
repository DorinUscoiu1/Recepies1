package com.example.proiect_android

import android.os.AsyncTask
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proiect_android.adapter.MainCategoryAdapter
import com.example.proiect_android.adapter.SubCategoryAdapter
import com.example.proiect_android.databinding.ActivityHome1Binding
import com.example.proiect_android.entites.Recipe
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class HomeActivity1 : AppCompatActivity() {

    private lateinit var binding: ActivityHome1Binding
    private var arrMainCategory = ArrayList<Recipe>()
    private var arrSubCategory = ArrayList<Recipe>()
    private var mainCategoryAdapter = MainCategoryAdapter {}
    private var subCategoryAdapter = SubCategoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHome1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup main category RecyclerView
        binding.itemShowHome.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.itemShowHome.adapter = mainCategoryAdapter

        // Setup subcategory RecyclerView
        binding.subitemShowHome.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.subitemShowHome.adapter = subCategoryAdapter

        // Setup SearchView listener
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { filterRecipes(it) }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { filterRecipes(it) }
                return false
            }
        })

        val url = "https://www.themealdb.com/api/json/v1/1/search.php?s="
        AsyncTaskHandleJson().execute(url)
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

        val list = ArrayList<Recipe>()

        for (i in 0 until jsonArray.length()) {
            val mealObject = jsonArray.getJSONObject(i)

            val id = mealObject.getString("idMeal").toInt()
            val name = mealObject.getString("strMeal")

            list.add(Recipe(id, name))
        }

        arrMainCategory.clear()
        arrMainCategory.addAll(list)

        arrSubCategory.clear()
        arrSubCategory.add(Recipe(1, "Beef and mustard pie"))
        arrSubCategory.add(Recipe(2, "Chips and cheese"))
        arrSubCategory.add(Recipe(3, "French fries with chicken"))
        arrSubCategory.add(Recipe(4, "Fruit with cream"))
        mainCategoryAdapter.setData(arrMainCategory)
        subCategoryAdapter.setData(arrSubCategory)
    }


    private fun filterRecipes(query: String) {
        val filteredMainCategory = arrMainCategory.filter { it.DishName.contains(query, ignoreCase = true) }
        val filteredSubCategory = arrSubCategory.filter { it.DishName.contains(query, ignoreCase = true) }

        mainCategoryAdapter.setData(filteredMainCategory)
        subCategoryAdapter.setData(filteredSubCategory)
    }
}
