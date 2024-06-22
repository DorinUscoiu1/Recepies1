package com.example.proiect_android.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room
import com.example.proiect_android.entites.*
import com.example.proiect_android.dao.RecipeDao
import android.content.Context
@Database(entities = [Recipe::class], version = 1,exportSchema = false)
abstract class RecipeDatabase :RoomDatabase(){
    companion object{
       var recipesDatabase:RecipeDatabase?=null

        @Synchronized
        fun getDatabase(context:Context):RecipeDatabase{
            if(recipesDatabase!==null){
                recipesDatabase=Room.databaseBuilder(
                    context,
                    RecipeDatabase::class.java,
                     "recipe.db"
                ).build()
            }
            return recipesDatabase!!
        }
    }
    abstract fun recipeDao():RecipeDao

}