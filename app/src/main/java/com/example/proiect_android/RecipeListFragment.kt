package com.example.proiect_android

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.proiect_android.adapter.MainCategoryAdapter
import com.example.proiect_android.database.RecipeDatabase
import com.example.proiect_android.databinding.FragmentRecipeListBinding
import com.example.proiect_android.entites.Recipe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeListFragment : Fragment() {

    private var _binding: FragmentRecipeListBinding? = null
    private val binding get() = _binding!!

    private var arrMainCategory = ArrayList<Recipe>()
    private lateinit var mainCategoryAdapter: MainCategoryAdapter

    private lateinit var recipeDatabase: RecipeDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recipeDatabase = Room.databaseBuilder(requireContext(), RecipeDatabase::class.java, "recipe.db").build()

        mainCategoryAdapter = MainCategoryAdapter { recipe ->
            val intent = Intent(activity, RecipeDetailActivity::class.java)
            intent.putExtra("RECIPE_DETAIL", recipe)
            startActivity(intent)
        }

        binding.recyclerViewRecipes.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewRecipes.adapter = mainCategoryAdapter

        loadRecipesFromDatabase()
    }

    private fun loadRecipesFromDatabase() {
        CoroutineScope(Dispatchers.IO).launch {
            val recipes = recipeDatabase.recipeDao().getAllRecipes()
            arrMainCategory.clear()
            arrMainCategory.addAll(recipes)
            requireActivity().runOnUiThread {
                mainCategoryAdapter.setData(arrMainCategory)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
