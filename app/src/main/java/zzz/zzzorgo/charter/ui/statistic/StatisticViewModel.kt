package zzz.zzzorgo.charter.ui.statistic

import androidx.lifecycle.*
import com.github.mikephil.charting.data.Entry
import kotlinx.coroutines.launch
import zzz.zzzorgo.charter.data.model.Record
import zzz.zzzorgo.charter.data.repo.CurrencyRepository
import zzz.zzzorgo.charter.data.repo.RecordRepository
import zzz.zzzorgo.charter.utils.getSeconds
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject

class StatisticViewModel @Inject constructor(
    recordRepository: RecordRepository,
    private val currencyRepository: CurrencyRepository
): ViewModel() {

    val balanceEntries: LiveData<List<Entry>>
    var allCurrencies: LiveData<Set<Currency>>
    private val balanceEntriesDeps: MediatorLiveData<BalanceEntriesDepsData> = MediatorLiveData()

    init {
        balanceEntriesDeps.addSource(recordRepository.allRecords) {
            val prevValue = balanceEntriesDeps.value
            val newValue = BalanceEntriesDepsData(it, prevValue?.currencyValues ?: mutableMapOf())
            balanceEntriesDeps.value = newValue
        }

        balanceEntriesDeps.addSource(currencyRepository.mapCurrencyToValue) {
            val prevValue = balanceEntriesDeps.value
            val newValue = BalanceEntriesDepsData(prevValue?.records ?: emptyList(), it)
            balanceEntriesDeps.value = newValue
        }

        allCurrencies = Transformations.map(recordRepository.allRecords) { records ->

            records.fold(mutableSetOf<Currency>()) { acc, record ->
                when(record.type) {
                    Record.Type.TRANSFER -> acc.addAll(setOf(record.currencyFrom as Currency, record.currencyTo as Currency))
                    Record.Type.OUTCOME -> acc.add(record.currencyFrom as Currency)
                    Record.Type.INCOME -> acc.add(record.currencyTo as Currency)
                }

                acc
            }
        }

        balanceEntries = Transformations.map(balanceEntriesDeps) { deps ->
            val records = deps.records.reversed()
            val firstRecordDate = if (records.isEmpty())
                LocalDateTime.now() else
                records.first().date

            val result = records.fold(ChartPointsAccumulator()) { acc, record ->
                when(record.type) {
                    Record.Type.INCOME -> {
                        val currency = record.currencyTo as Currency
                        val currencyTotal = acc.currencyTotalMap[currency] ?: BigDecimal.ZERO
                        acc.currencyTotalMap[currency] = currencyTotal + record.valueTo as BigDecimal
                    }
                    Record.Type.OUTCOME -> {
                        val currency = record.currencyFrom as Currency
                        val currencyTotal = acc.currencyTotalMap[currency] ?: BigDecimal.ZERO
                        acc.currencyTotalMap[currency] = currencyTotal - record.valueFrom as BigDecimal
                    }
                    Record.Type.TRANSFER -> {
                        val currencyFrom = record.currencyFrom as Currency
                        val currencyTotalFrom = acc.currencyTotalMap[currencyFrom] ?: BigDecimal.ZERO
                        val currencyTo = record.currencyTo as Currency
                        val currencyTotalTo = acc.currencyTotalMap[currencyTo] ?: BigDecimal.ZERO

                        acc.currencyTotalMap[currencyFrom] = currencyTotalFrom - record.valueFrom as BigDecimal
                        acc.currencyTotalMap[currencyTo] = currencyTotalTo + record.valueTo as BigDecimal
                    }
                }

                val x = getSeconds(record.date).toFloat()
                val y = acc.getCurrentTotal(deps.currencyValues)
                acc.points.add(Entry(x, y))
                acc
            }

            result.points.add(0, Entry(getSeconds(firstRecordDate).toFloat(), 0F))

            result.points
        }

    }

    fun updateCurrencyValues() = viewModelScope.launch {
        currencyRepository.getCbrCurrencyForToday()
    }

    class ChartPointsAccumulator {
        val currencyTotalMap = mutableMapOf<Currency, BigDecimal>()
        val points = mutableListOf<Entry>()

        fun getCurrentTotal(currencyMultiplier: MutableMap<Currency, String>): Float {
            return currencyTotalMap.entries.fold(BigDecimal.ZERO) { acc, entry ->
                acc + entry.value * BigDecimal(currencyMultiplier[entry.key] ?: "1")
            }.toFloat()
        }
    }
}

class BalanceEntriesDepsData(
    val records: List<Record>,
    val currencyValues: MutableMap<Currency, String>
)
