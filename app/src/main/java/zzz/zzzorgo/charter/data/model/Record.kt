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

    enum class Status {
        NORMAL, HIDDEN, DELETED
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
