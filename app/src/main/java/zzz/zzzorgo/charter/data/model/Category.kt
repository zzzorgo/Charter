package zzz.zzzorgo.charter.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_table")
data class Category(val name: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    var income: Boolean = false
    var outcome: Boolean = true
    var deleted: Boolean = false

    companion object {
        val nullCategory = Category("nullCategory").apply {
            id = 1
            income = true
            outcome = true
        }
    }
}
