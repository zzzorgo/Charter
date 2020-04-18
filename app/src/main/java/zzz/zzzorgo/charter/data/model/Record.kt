package zzz.zzzorgo.charter.data.model

import androidx.room.*
import java.math.BigDecimal

@Entity(tableName = "record_table")
data class Record(val value: BigDecimal) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
