package zzz.zzzorgo.charter.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

import zzz.zzzorgo.charter.data.model.Record

@Dao
interface RecordDao {

    @Query("SELECT * from record_table ORDER BY id ASC")
    fun getRecords(): LiveData<List<Record>>

    @Insert
    suspend fun insert(record: Record)

    @Query("DELETE FROM record_table")
    suspend fun deleteAll()
}