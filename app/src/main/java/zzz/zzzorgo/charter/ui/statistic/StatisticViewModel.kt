package zzz.zzzorgo.charter.ui.statistic

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.Entry
import zzz.zzzorgo.charter.data.model.Record
import zzz.zzzorgo.charter.data.repo.RecordRepository
import javax.inject.Inject

class StatisticViewModel @Inject constructor(
    private var recordRepository: RecordRepository
): ViewModel() {

    val balanceEntries: LiveData<List<Entry>>

    init {
        balanceEntries = Transformations.map(recordRepository.allRecords) {
            it.fold(mutableListOf(Entry(0F, 0F))) { acc, record ->
                val lastEntry = acc.last()
                val x = lastEntry.x + 1
                val entry = when(record.type) {
                    Record.Type.INCOME -> Entry(x, lastEntry.y + record.valueTo!!.toFloat())
                    Record.Type.OUTCOME -> Entry(x, lastEntry.y - record.valueFrom!!.toFloat())
                    Record.Type.TRANSFER -> Entry(x, lastEntry.y)
                }
                acc.add(entry)
                acc
            }
        }
    }
}
