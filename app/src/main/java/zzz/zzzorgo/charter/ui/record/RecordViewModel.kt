package zzz.zzzorgo.charter.ui.record

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import zzz.zzzorgo.charter.data.AppDatabase
import zzz.zzzorgo.charter.data.model.Account
import zzz.zzzorgo.charter.data.model.Category
import zzz.zzzorgo.charter.data.model.Record
import zzz.zzzorgo.charter.data.repo.AccountRepository
import zzz.zzzorgo.charter.data.repo.CategoryRepository
import zzz.zzzorgo.charter.data.repo.RecordRepository


// Class extends AndroidViewModel and requires application as a parameter.
class RecordViewModel(application: Application) : AndroidViewModel(application) {

    // The ViewModel maintains a reference to the repository to get data.
    private val recordRepository: RecordRepository
    private val categoryRepository: CategoryRepository
    private val accountRepository: AccountRepository

    // LiveData gives us updated words when they change.
    val allRecords: LiveData<List<Record>>
    val allCategories: LiveData<List<Category>>
    val allAccounts: LiveData<List<Account>>

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct
        // the correct WordRepository.
        val database = AppDatabase.getDatabase(application, viewModelScope)
        recordRepository = RecordRepository(database.recordDao())
        categoryRepository = CategoryRepository(database.categoryDao())
        accountRepository = AccountRepository(database.accountDao())

        allRecords = recordRepository.allRecords
        allCategories = categoryRepository.allCategories
        allAccounts = accountRepository.allAccounts
    }

    /**
     * The implementation of insert() in the database is completely hidden from the UI.
     * Room ensures that you're not doing any long running operations on
     * the main thread, blocking the UI, so we don't need to handle changing Dispatchers.
     * ViewModels have a coroutine scope based on their lifecycle called
     * viewModelScope which we can use here.
     */
    fun insert(record: Record) = viewModelScope.launch {
        recordRepository.insert(record)
    }

    fun getRecords(): LiveData<List<Record>> {
        return allRecords
    }
}