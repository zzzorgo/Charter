package zzz.zzzorgo.charter.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import zzz.zzzorgo.charter.data.model.Account

@Dao
interface AccountDao {
    @Query("SELECT * from account_table ORDER BY id ASC")
    fun getAccounts(): LiveData<List<Account>>

    @Insert
    suspend fun insert(account: Account)

    @Query("DELETE FROM account_table")
    suspend fun deleteAll()
}