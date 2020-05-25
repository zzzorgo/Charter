package zzz.zzzorgo.charter.data

//todo понять шо це таке

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import zzz.zzzorgo.charter.data.dao.AccountDao
import zzz.zzzorgo.charter.data.dao.CategoryDao
import zzz.zzzorgo.charter.data.dao.RecordDao
import zzz.zzzorgo.charter.data.dao.SettingsDao
import zzz.zzzorgo.charter.data.model.Account
import zzz.zzzorgo.charter.data.model.Category
import zzz.zzzorgo.charter.data.model.Record
import zzz.zzzorgo.charter.data.model.Settings
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Database(entities = [Record::class, Account::class, Category::class, Settings::class], version = 2, exportSchema = true)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun recordDao(): RecordDao
    abstract fun accountDao(): AccountDao
    abstract fun settingsDao(): SettingsDao
    abstract fun categoryDao(): CategoryDao
}

class Converters {
    @TypeConverter
    fun fromLong(value: Long?): BigDecimal? {
        return if (value == null) null else BigDecimal(value).divide(BigDecimal(100L))
    }

    @TypeConverter
    fun toLong(bigDecimal: BigDecimal?): Long? {
        return bigDecimal?.multiply(BigDecimal(100L))?.toLong()
    }

    @TypeConverter
    fun fromCurrency(value: Currency?): String? {
        return value?.currencyCode
    }

    @TypeConverter
    fun toCurrency(value: String?): Currency? {
        return if (value != null) Currency.getInstance(value) else null
    }

    @TypeConverter
    fun fromLocalDateTime(value: LocalDateTime): String {
        return value.toString()
    }

    @TypeConverter
    fun toLocalDateTime(value: String): LocalDateTime {
        return LocalDateTime.parse(value)
    }
}

