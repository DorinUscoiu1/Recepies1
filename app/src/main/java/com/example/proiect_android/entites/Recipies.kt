package com.example.proiect_android.entites

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName="Recipes")
data class Recipies(
    @PrimaryKey(autoGenerate = true)
    val id:Int
) :Serializable