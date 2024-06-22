package com.example.proiect_android.dao
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.proiect_android.entites.*
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.example.proiect_android.entites.Recipe

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes")
    fun getAllRecipes(): List<Recipe>

    @Update
    fun update(recipe: Recipe)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipies: Recipe)

    @Delete
    fun delete(recipies: Recipe)


}