package zzz.zzzorgo.charter.data.repo

import androidx.lifecycle.LiveData
import zzz.zzzorgo.charter.data.dao.CategoryDao
import zzz.zzzorgo.charter.data.model.Category
import javax.inject.Inject

class CategoryRepository @Inject constructor(private val categoryDao: CategoryDao) {
    val allCategories: LiveData<List<Category>> = categoryDao.getCategories()

    suspend fun insert(category: Category) {
        categoryDao.insert(category)
    }
}
