package zzz.zzzorgo.charter.data.repo

import androidx.lifecycle.LiveData
import zzz.zzzorgo.charter.data.dao.AccountDao
import zzz.zzzorgo.charter.data.model.Account

class AccountRepository(private val accountDao: AccountDao) {
    val allRecords: LiveData<List<Account>> = accountDao.getAccounts()

    suspend fun insert(account: Account) {
        accountDao.insert(account)
    }
}
