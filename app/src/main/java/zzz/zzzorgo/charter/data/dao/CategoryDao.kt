package zzz.zzzorgo.charter.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import zzz.zzzorgo.charter.data.model.Category

@Dao
interface CategoryDao {
    @Query("SELECT * from category_table ORDER BY id ASC")
    fun getCategories(): LiveData<List<Category>>

    @Insert
    suspend fun insert(category: Category)

    @Query("DELETE FROM category_table")
    suspend fun deleteAll()
}