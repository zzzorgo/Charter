package zzz.zzzorgo.charter.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import zzz.zzzorgo.charter.data.model.Account
import zzz.zzzorgo.charter.data.repo.AccountRepository
import zzz.zzzorgo.charter.data.repo.SettingsRepository
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject

data class AccountWithTotal(val account: Account, val total: BigDecimal)

// Class extends AndroidViewModel and requires application as a parameter.
class AccountViewModel @Inject constructor(
    private var accountRepository: AccountRepository,
    settingsRepository: SettingsRepository
) : ViewModel() {

    var currency: LiveData<Currency> = Transformations.map(settingsRepository.settings) {
        it.mainCurrency
    }
    var accountsWithTotal: LiveData<List<AccountWithTotal>>

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

    init {
        accountsWithTotal = Transformations.map(accountRepository.accountsWithRecords) {
            it.map { accountWithRecord ->
                val totalIncome = accountWithRecord.incomeRecords.fold(BigDecimal.ZERO) { acc, record -> acc + (record.valueTo ?: BigDecimal.ZERO) }
                val totalOutcome = accountWithRecord.outcomeRecords.fold(BigDecimal.ZERO) { acc, record -> acc + (record.valueFrom ?: BigDecimal.ZERO) }
                val total = accountWithRecord.account.initialValue + totalIncome - totalOutcome
                AccountWithTotal(accountWithRecord.account, total)
            }
        }
    }
}