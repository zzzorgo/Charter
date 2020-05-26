package zzz.zzzorgo.charter.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import zzz.zzzorgo.charter.data.model.Record

@Dao
interface RecordDao {

    @Query("SELECT * from record_table ORDER BY date DESC")
    fun getRecords(): LiveData<List<Record>>

    @Insert
    suspend fun insert(record: Record)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg records: Record)

    @Query("DELETE FROM record_table")
    suspend fun deleteAll()
}