package zzz.zzzorgo.charter.data.model

import androidx.room.*
import java.math.BigDecimal

@Entity(tableName = "account_table")
data class Account(val name: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
