package zzz.zzzorgo.charter.data.migration

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import zzz.zzzorgo.charter.data.AppDatabase
import zzz.zzzorgo.charter.data.model.Account
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class Migration_1_2_Test {
    private val TEST_DB_NAME = "migration-test"

    @get:Rule
    public val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        AppDatabase::class.java.canonicalName,
        FrameworkSQLiteOpenHelperFactory()
    )

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private fun insertAccount(account: Account, db: SupportSQLiteDatabase) {
        val values = ContentValues()
        values.put("id", account.id)
        values.put("name", account.name)
        db.insert("account_table", SQLiteDatabase.CONFLICT_REPLACE, values)
    }

    @Test
    @Throws(IOException::class)
    fun migrate1To2() {
        var db = helper.createDatabase(TEST_DB_NAME, 1).apply {
            execSQL("INSERT INTO account_table (name) VALUES ('lol')")
            close()
        }

        // Re-open the database with version 2 and provide
        // MIGRATION_1_2 as the migration process.
        db = helper.runMigrationsAndValidate(TEST_DB_NAME, 2, true, MIGRATION_1_2).apply {
            val result = db.query("SELECT * FROM account_table")
//            close()
        }

        // MigrationTestHelper automatically verifies the schema changes,
        // but you need to validate that the data was migrated properly.
    }

    private val ALL_MIGRATIONS = arrayOf(MIGRATION_1_2)
    @Test
    @Throws(IOException::class)
    fun migrateAll() {
        // Create earliest version of the database.
        helper.createDatabase(TEST_DB_NAME, 1).apply {
//            execSQL("INSERT INTO account_table ('id','name') VALUES (1, 'lol1')")
//            execSQL("INSERT INTO account_table ('id', 'name') VALUES (2, 'lol2')")
//            execSQL("INSERT INTO account_table ('id', 'name') VALUES (3, 'lol3')")
//            execSQL("INSERT INTO account_table ('id', 'name') VALUES (4, 'lol4')")
            insertAccount(Account("lol34"), this)
            insertAccount(Account("lol1"), this)
            insertAccount(Account("lol2"), this)
//            var statement = compileStatement("INSERT INTO account_table ('name') VALUES ('lol1')")
//            statement.executeInsert()
//            statement = compileStatement("INSERT INTO account_table ('name') VALUES ('lol2')")
//            statement.executeInsert()
//            statement = compileStatement("INSERT INTO account_table ('name') VALUES ('lol3')")
//            statement.executeInsert()

//            var result: Array<Object> = arrayOf()
//            this.query("SELECT * FROM account_table", result)
//            result
//            compileStatement("SELECT name FROM account_table").simpleQueryForString()
            close()
        }

        // Open latest version of the database. Room will validate the schema
        // once all migrations execute.
        Room.databaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            AppDatabase::class.java,
            TEST_DB_NAME
        ).addMigrations(*ALL_MIGRATIONS).build().apply {
            openHelper.writableDatabase
            val accounts = this.accountDao().getAccounts()
            assertEquals(accounts.value?.size, 1)

            close()
        }
    }
}

inline fun <reified T> lambdaMock(): T = mock(T::class.java)

class Presenter {

    val titleLiveData = MutableLiveData<List<Account>>()

    fun observeTitleChanges(lifecycle: Lifecycle, observer: (List<Account>) -> Unit) {
        titleLiveData.observe({ lifecycle }) { accounts ->
            accounts?.let(observer)
        }
    }
}
class PresenterTests {

    @Test
    fun showTitleTest() {
        val presenter = Presenter()

        val observer = lambdaMock<(List<Account>) -> Unit>()
        val lifecycle = LifecycleRegistry(mock(LifecycleOwner::class.java))
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

        presenter.observeTitleChanges(lifecycle, observer)

        presenter.titleLiveData =

        verify(observer).invoke("title")
    }
}