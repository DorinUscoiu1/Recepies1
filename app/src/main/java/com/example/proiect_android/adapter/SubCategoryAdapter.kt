package com.example.proiect_android.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proiect_android.databinding.SubitemShowHomeBinding
import com.example.proiect_android.entites.Category

class SubCategoryAdapter(private val onCategoryClick: (Category) -> Unit) : RecyclerView.Adapter<SubCategoryAdapter.CategoryViewHolder>() {
    private var arrSubCategory = ArrayList<Category>()

    class CategoryViewHolder(private val binding: SubitemShowHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category, onClick: (Category) -> Unit) {
            binding.tvCategoryName.text = category.categoryName
            binding.root.setOnClickListener {
                onClick(category)
            }
        }
    }

    fun setData(arrData: List<Category>) {
        arrSubCategory.clear()
        arrSubCategory.addAll(arrData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = SubitemShowHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return arrSubCategory.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = arrSubCategory[position]
        holder.bind(category, onCategoryClick)
    }
}
