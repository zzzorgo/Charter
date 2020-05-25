package zzz.zzzorgo.charter.ui.misc

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import zzz.zzzorgo.charter.data.model.Category
import zzz.zzzorgo.charter.data.repo.CategoryRepository
import javax.inject.Inject

class CategoryManagerViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {
    val allCategories: LiveData<List<Category>> = categoryRepository.allCategories

    fun insert(category: Category) = viewModelScope.launch {
        categoryRepository.insert(category)
    }
}