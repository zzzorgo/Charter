package zzz.zzzorgo.charter.data.model

import androidx.room.*
import java.math.BigDecimal
import java.util.*

@Entity(tableName = "account_table")
data class Account(var name: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    @ColumnInfo(defaultValue = "0")
    var initialValue: BigDecimal = BigDecimal("0")

    lateinit var currency: Currency

    var deleted: Boolean = false
}

class AccountWithRecords {
    @Embedded
    lateinit var account: Account

    @Relation(parentColumn = "id", entityColumn = "accountFrom")
    var outcomeRecords: List<Record> = ArrayList()

    @Relation(parentColumn = "id", entityColumn = "accountTo")
    var incomeRecords: List<Record> = ArrayList()
}
