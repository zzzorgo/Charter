package zzz.zzzorgo.charter.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Entity(tableName = "record_table", foreignKeys = [
    ForeignKey(childColumns = ["category"], parentColumns = ["id"], entity = Category::class),
    ForeignKey(childColumns = ["accountFrom"], parentColumns = ["id"], entity = Account::class),
    ForeignKey(childColumns = ["accountTo"], parentColumns = ["id"], entity = Account::class)
])
data class Record(val category: Int = 0) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    var valueFrom: BigDecimal? = BigDecimal.ZERO
    var valueTo: BigDecimal? = BigDecimal.ZERO

    var accountFrom: Int? = null
    var accountTo: Int? = null

    var currencyFrom: Currency? = null
    var currencyTo: Currency? = null

    var date: LocalDateTime = LocalDateTime.now()

    var deleted: Boolean = false
}
