package com.example.proiect_android.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proiect_android.databinding.SubitemShowHomeBinding
import com.example.proiect_android.entites.Recipe

class SubCategoryAdapter : RecyclerView.Adapter<SubCategoryAdapter.RecipeViewHolder>() {
    private var arrSubCategory = ArrayList<Recipe>()

    class RecipeViewHolder(private val binding: SubitemShowHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Recipe) {
            binding.tvDishName.text = recipe.DishName
            // You can bind other views here using binding object
        }
    }

    fun setData(arrData: List<Recipe>) {
        arrSubCategory.clear()
        arrSubCategory.addAll(arrData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = SubitemShowHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return arrSubCategory.size
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = arrSubCategory[position]
        holder.bind(recipe)
    }
}
