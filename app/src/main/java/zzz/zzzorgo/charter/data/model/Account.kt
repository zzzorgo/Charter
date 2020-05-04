package zzz.zzzorgo.charter.data.model

import androidx.room.*
import java.math.BigDecimal

@Entity(tableName = "account_table")
data class Account(var name: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(defaultValue = "0")
    var total: BigDecimal = BigDecimal("0")
}
