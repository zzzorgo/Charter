package zzz.zzzorgo.charter.ui.misc

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import zzz.zzzorgo.charter.data.AppDatabase
import zzz.zzzorgo.charter.data.model.Category
import zzz.zzzorgo.charter.data.repo.CategoryRepository

class CategoryManagerViewModel (application: Application) : AndroidViewModel(application) {
    private val categoryRepository: CategoryRepository

    val allCategories: LiveData<List<Category>>

    init {
        val database = AppDatabase.getDatabase(application, viewModelScope);
        categoryRepository = CategoryRepository(database.categoryDao())
        allCategories = categoryRepository.allCategories
    }

    fun insert(category: Category) = viewModelScope.launch {
        categoryRepository.insert(category)
    }
}