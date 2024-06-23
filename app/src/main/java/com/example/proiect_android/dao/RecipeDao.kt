package com.example.proiect_android.dao
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
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
    fun insert(recipies: List<Recipe>)

    @Delete
    fun delete(recipies: Recipe)


}