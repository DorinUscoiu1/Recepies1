package com.example.proiect_android.entites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "dishName") var DishName: String,
    @ColumnInfo(name = "category") var Category: String,
    @ColumnInfo(name = "instructions") var Instructions: String,
    @ColumnInfo(name = "ingredients") var Ingredients: String
) : Serializable
