package zzz.zzzorgo.charter.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "settings_table")
data class Settings(val mainCurrency: Currency) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
