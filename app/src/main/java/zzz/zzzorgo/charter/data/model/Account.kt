package zzz.zzzorgo.charter.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.*

@Entity(tableName = "account_table")
data class Account(var name: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null

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
