package zzz.zzzorgo.charter.data.model

import androidx.room.*
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Entity(tableName = "record_table", foreignKeys = [
    ForeignKey(childColumns = ["category"], parentColumns = ["id"], entity = Category::class),
    ForeignKey(childColumns = ["accountFrom"], parentColumns = ["id"], entity = Account::class),
    ForeignKey(childColumns = ["accountTo"], parentColumns = ["id"], entity = Account::class)
])
data class Record(val category: Long = 0) {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null

    var valueFrom: BigDecimal? = BigDecimal.ZERO
    var valueTo: BigDecimal? = BigDecimal.ZERO

    var accountFrom: Long? = null
    var accountTo: Long? = null

    var currencyFrom: Currency? = null
    var currencyTo: Currency? = null

    var date: LocalDateTime = LocalDateTime.now()

    @TypeConverters(StatusConverter::class)
    var status: Status = Status.NORMAL

    val type get() = when {
        valueFrom != BigDecimal.ZERO && valueTo == BigDecimal.ZERO -> Type.OUTCOME
        valueTo != BigDecimal.ZERO && valueFrom == BigDecimal.ZERO -> Type.INCOME
        valueFrom != BigDecimal.ZERO && valueTo != BigDecimal.ZERO -> Type.TRANSFER
        else -> throw IllegalStateException("Record is not properly initialized")
    }

    enum class Status {
        NORMAL, HIDDEN, DELETED
    }

    enum class Type {
        INCOME, OUTCOME, TRANSFER
    }

    class StatusConverter {
        @TypeConverter
        fun toStatus(statusName: String): Status {
            return Status.valueOf(statusName)
        }

        @TypeConverter
        fun fromStatus(status: Status): String {
            return status.name
        }
    }
}
