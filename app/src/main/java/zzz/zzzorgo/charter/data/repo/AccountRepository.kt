package zzz.zzzorgo.charter.data.repo

import androidx.lifecycle.LiveData
import zzz.zzzorgo.charter.data.dao.AccountDao
import zzz.zzzorgo.charter.data.model.Account
import zzz.zzzorgo.charter.data.model.AccountWithRecords

class AccountRepository(private val accountDao: AccountDao) {
    val allAccounts: LiveData<List<Account>> = accountDao.getAccounts()
    val accountsWithRecords: LiveData<List<AccountWithRecords>> = accountDao.getAccountsWithRecords()

    suspend fun insert(account: Account) {
        accountDao.insert(account)
    }
}
