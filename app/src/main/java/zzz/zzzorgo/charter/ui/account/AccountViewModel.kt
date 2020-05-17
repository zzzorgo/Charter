package zzz.zzzorgo.charter.ui.account

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import zzz.zzzorgo.charter.data.AppDatabase
import zzz.zzzorgo.charter.data.model.Account
import zzz.zzzorgo.charter.data.repo.AccountRepository
import zzz.zzzorgo.charter.data.repo.SettingsRepository
import java.math.BigDecimal
import java.util.*

data class AccountWithTotal(val account: Account, val total: BigDecimal)

// Class extends AndroidViewModel and requires application as a parameter.
class AccountViewModel  (application: Application) : AndroidViewModel(application) {
    private val accountRepository: AccountRepository
    private val settingsRepository: SettingsRepository
    val currency: LiveData<Currency>
    val accountsWithTotal: LiveData<List<AccountWithTotal>>

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct
        // the correct WordRepository.
        val database = AppDatabase.getDatabase(application, viewModelScope);
        accountRepository = AccountRepository(database.accountDao())

        settingsRepository = SettingsRepository(database.settingsDao())
        currency = Transformations.map(settingsRepository.settings) {
            it.mainCurrency
        }

        accountsWithTotal = Transformations.map(accountRepository.accountsWithRecords) {
            it.map { accountWithRecord ->
                val totalIncome = accountWithRecord.incomeRecords.fold(BigDecimal.ZERO) { acc, record -> acc + (record.valueTo ?: BigDecimal.ZERO) }
                val totalOutcome = accountWithRecord.outcomeRecords.fold(BigDecimal.ZERO) { acc, record -> acc + (record.valueFrom ?: BigDecimal.ZERO) }
                val total = accountWithRecord.account.initialValue + totalIncome - totalOutcome
                AccountWithTotal(accountWithRecord.account, total)
            }
        }
    }

    /**
     * The implementation of insert() in the database is completely hidden from the UI.
     * Room ensures that you're not doing any long running operations on
     * the main thread, blocking the UI, so we don't need to handle changing Dispatchers.
     * ViewModels have a coroutine scope based on their lifecycle called
     * viewModelScope which we can use here.
     */
    fun insert(account: Account) = viewModelScope.launch {
        accountRepository.insert(account)
    }
}