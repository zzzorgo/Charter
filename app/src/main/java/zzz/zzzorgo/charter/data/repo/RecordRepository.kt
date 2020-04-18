package zzz.zzzorgo.charter.data.repo

import androidx.lifecycle.LiveData
import zzz.zzzorgo.charter.data.dao.RecordDao
import zzz.zzzorgo.charter.data.model.Record

class RecordRepository(private val recordDao: RecordDao) {
    val allRecords: LiveData<List<Record>> = recordDao.getRecords()

    suspend fun insert(record: Record) {
        recordDao.insert(record)
    }
}
