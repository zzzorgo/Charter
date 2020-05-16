package zzz.zzzorgo.charter.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.util.*

@Entity(tableName = "record_table")
data class Record(val value: BigDecimal) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    var category: Int = 0

    var valueFrom: BigDecimal = BigDecimal.ZERO
    var valueTo: BigDecimal = BigDecimal.ZERO

    var accountFrom: Int = 0
    var accountTo: Int = 0

    lateinit var currencyFrom: Currency
    lateinit var currencyTo: Currency

    var deleted: Boolean = false
}
