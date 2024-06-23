package com.example.proiect_android.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proiect_android.databinding.ItemShowHomeBinding
import com.example.proiect_android.entites.Recipe

class MainCategoryAdapter(private val onCategoryClick: (String) -> Unit) : RecyclerView.Adapter<MainCategoryAdapter.RecipeViewHolder>() {
    private var arrMainCategory = ArrayList<Recipe>()

    class RecipeViewHolder(private val binding: ItemShowHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun getBinding(): ItemShowHomeBinding {
            return binding
        }
    }

    fun setData(arrData: List<Recipe>) {
        arrMainCategory.clear()
        arrMainCategory.addAll(arrData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = ItemShowHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return arrMainCategory.size
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = arrMainCategory[position]
        holder.getBinding().tvDishName.text = recipe.DishName
        holder.getBinding().root.setOnClickListener {
            onCategoryClick(recipe.DishName)
        }
    }
}
