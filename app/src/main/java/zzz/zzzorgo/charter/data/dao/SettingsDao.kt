package zzz.zzzorgo.charter.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import zzz.zzzorgo.charter.data.model.Settings

@Dao
interface SettingsDao {
    @Query("SELECT * from settings_table LIMIT 1")
    fun getSettings(): LiveData<Settings>

    @Query("DELETE FROM settings_table")
    suspend fun deleteAll()

    @Update
    suspend fun update(settings: Settings)

    @Insert
    suspend fun insert(settings: Settings)
}