package zzz.zzzorgo.charter.data

//todo понять шо це таке

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import zzz.zzzorgo.charter.data.dao.AccountDao
import zzz.zzzorgo.charter.data.dao.CategoryDao
import zzz.zzzorgo.charter.data.dao.RecordDao
import zzz.zzzorgo.charter.data.dao.SettingsDao
import zzz.zzzorgo.charter.data.migration.MIGRATION_1_2
import zzz.zzzorgo.charter.data.migration.MIGRATION_2_3
import zzz.zzzorgo.charter.data.model.Account
import zzz.zzzorgo.charter.data.model.Category
import zzz.zzzorgo.charter.data.model.Record
import zzz.zzzorgo.charter.data.model.Settings
import java.math.BigDecimal
import java.util.*

@Database(entities = [Record::class, Account::class, Category::class, Settings::class], version = 3, exportSchema = true)
@TypeConverters(Converters::class)
public abstract class AppDatabase : RoomDatabase() {

    abstract fun recordDao(): RecordDao
    abstract fun accountDao(): AccountDao
    abstract fun settingsDao(): SettingsDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "database"
                )
                .addCallback(InitiateCallback(scope))
                .addMigrations(MIGRATION_1_2)
                .addMigrations(MIGRATION_2_3)
                .build()
                INSTANCE = instance
                return instance
            }
        }

        private class InitiateCallback(
                private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                INSTANCE?.let { database ->
                    scope.launch {
                        populateDatabase(
                            database.recordDao(),
                            database.accountDao(),
                            database.settingsDao(),
                            database.categoryDao()
                        )
                    }
                }
            }

            suspend fun populateDatabase(
                recordDao: RecordDao,
                accountDao: AccountDao,
                settingsDao: SettingsDao,
                categoryDao: CategoryDao
            ) {
                // Delete all content here.
                recordDao.deleteAll()
                accountDao.deleteAll()
                settingsDao.deleteAll()
                categoryDao.deleteAll()

                val defaultCurrency = Currency.getInstance(Locale.getDefault())

                val account = Account("Наличные").apply {
                    currency = defaultCurrency
                }
                accountDao.insert(account)

                val settings = Settings(defaultCurrency)
                settingsDao.insert(settings)

                val nullCategory = Category("nullCategory").apply {
                    income = true
                    outcome = true
                }
                categoryDao.insert(nullCategory)
            }
        }
    }
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
    fun fromCurrency(value: Currency): String {
        return value.currencyCode
    }

    @TypeConverter
    fun toCurrency(value: String): Currency {
        return Currency.getInstance(value)
    }
}

