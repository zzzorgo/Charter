package zzz.zzzorgo.charter.data

//todo понять шо це таке
import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import zzz.zzzorgo.charter.data.dao.AccountDao
import java.math.BigDecimal

import zzz.zzzorgo.charter.data.dao.RecordDao
import zzz.zzzorgo.charter.data.migration.MIGRATION_1_2
import zzz.zzzorgo.charter.data.model.Account
import zzz.zzzorgo.charter.data.model.Record

@Database(entities = [Record::class, Account::class], version = 2, exportSchema = true)
@TypeConverters(Converters::class)
public abstract class AppDatabase : RoomDatabase() {

    abstract fun recordDao(): RecordDao
    abstract fun accountDao(): AccountDao

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
                        populateDatabase(database.recordDao(), database.accountDao())
                    }
                }
            }

            suspend fun populateDatabase(recordDao: RecordDao, accountDao: AccountDao) {
                // Delete all content here.
                recordDao.deleteAll()
                accountDao.deleteAll()

                // Add sample words.
                var record = Record(BigDecimal("1"))
                recordDao.insert(record)
                record = Record(BigDecimal("2"))
                recordDao.insert(record)

                // todo: move to string consts
                val account = Account("Наличные");
                accountDao.insert(account)

                // TODO: Add your own words!
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
}

