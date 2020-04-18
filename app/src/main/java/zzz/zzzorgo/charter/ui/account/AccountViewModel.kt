package zzz.zzzorgo.charter.ui.account

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import zzz.zzzorgo.charter.data.AppDatabase
import zzz.zzzorgo.charter.data.model.Account
import zzz.zzzorgo.charter.data.repo.AccountRepository


// Class extends AndroidViewModel and requires application as a parameter.
class AccountViewModel  (application: Application) : AndroidViewModel(application) {

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: AccountRepository
    // LiveData gives us updated words when they change.
    val allAccounts: LiveData<List<Account>>

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct
        // the correct WordRepository.
        val accountDao = AppDatabase.getDatabase(application, viewModelScope).accountDao()
        repository = AccountRepository(accountDao)
        allAccounts = repository.allRecords
    }

    /**
     * The implementation of insert() in the database is completely hidden from the UI.
     * Room ensures that you're not doing any long running operations on
     * the main thread, blocking the UI, so we don't need to handle changing Dispatchers.
     * ViewModels have a coroutine scope based on their lifecycle called
     * viewModelScope which we can use here.
     */
    fun insert(account: Account) = viewModelScope.launch {
        repository.insert(account)
    }

    fun getRecords(): LiveData<List<Account>> {
        return allAccounts
    }
}