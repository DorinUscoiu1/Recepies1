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
import android.content.Intent
import com.example.proiect_android.adapter.MainCategoryAdapter
import com.example.proiect_android.adapter.SubCategoryAdapter
import com.example.proiect_android.R
import com.example.proiect_android.databinding.ActivityHome1Binding

class HomeActivity1 : AppCompatActivity() {

    private lateinit var binding: ActivityHome1Binding
    var arrMainCategory = ArrayList<Recipe>()
    var arrSubCategory = ArrayList<Recipe>()
    var mainCategoryAdapter=MainCategoryAdapter()
    var subCategoryAdapter=SubCategoryAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHome1Binding.inflate(layoutInflater)
        setContentView(binding.root)
//temporary
        arrMainCategory.add(Recipe(1,"Beef"))
        arrMainCategory.add(Recipe(2,"Chips"))
        arrMainCategory.add(Recipe(3,"French fries"))
        arrMainCategory.add(Recipe(4,"Fruit"))

        mainCategoryAdapter.setData(arrMainCategory)

        arrSubCategory.add(Recipe(1,"Beef and mustard pie"))
        arrSubCategory.add(Recipe(2,"Chips and cheese"))
        arrSubCategory.add(Recipe(3,"French fries with chiken"))
        arrSubCategory.add(Recipe(4,"Fruit with cream"))

        subCategoryAdapter.setData(arrSubCategory)

        // Setup main category RecyclerView
        binding.itemShowHome.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.itemShowHome.adapter = mainCategoryAdapter

        // Setup subcategory RecyclerView
        binding.subitemShowHome.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.subitemShowHome.adapter = subCategoryAdapter
    }
    }



