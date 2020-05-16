package zzz.zzzorgo.charter.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.util.*

@Entity(tableName = "account_table")
data class Account(var name: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(defaultValue = "0")
    var initialValue: BigDecimal = BigDecimal("0")

    lateinit var currency: Currency

    var deleted: Boolean = false
}
