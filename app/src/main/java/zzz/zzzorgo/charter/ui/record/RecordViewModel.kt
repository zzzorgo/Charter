package zzz.zzzorgo.charter.ui.record

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import zzz.zzzorgo.charter.data.model.Account
import zzz.zzzorgo.charter.data.model.Category
import zzz.zzzorgo.charter.data.model.Record
import zzz.zzzorgo.charter.data.repo.AccountRepository
import zzz.zzzorgo.charter.data.repo.CategoryRepository
import zzz.zzzorgo.charter.data.repo.CurrencyRepository
import zzz.zzzorgo.charter.data.repo.RecordRepository
import javax.inject.Inject


// Class extends AndroidViewModel and requires application as a parameter.
class RecordViewModel @Inject constructor(
    private val recordRepository: RecordRepository,
    categoryRepository: CategoryRepository,
    accountRepository: AccountRepository,
    private val currencyRepository: CurrencyRepository
) : ViewModel() {
    // LiveData gives us updated words when they change.
    val allRecords: LiveData<List<Record>> = recordRepository.allRecords
    val allCategories: LiveData<List<Category>> = categoryRepository.allCategories
    val allAccounts: LiveData<List<Account>> = accountRepository.allAccounts

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