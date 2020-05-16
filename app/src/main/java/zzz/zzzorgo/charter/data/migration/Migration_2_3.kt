package zzz.zzzorgo.charter.data.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.beginTransaction()

            database.execSQL("""
                CREATE TABLE IF NOT EXISTS account_table_new (
                    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    name TEXT NOT NULL,
                    initialValue INTEGER NOT NULL DEFAULT 0,
                    currency TEXT NOT NULL DEFAULT "USD",
                    deleted BOOLEAN NOT NULL DEFAULT 0
                )
            """)

            database.execSQL("""
                INSERT INTO account_table_new(id, name, initialValue)
                SELECT id, name, total
                FROM account_table
            """)

            database.execSQL("DROP TABLE account_table")
            database.execSQL("ALTER TABLE account_table_new RENAME TO account_table")

        database.endTransaction()

        database.beginTransaction()

            database.execSQL("DROP TABLE record_table")
            database.execSQL("""
                CREATE TABLE IF NOT EXISTS record_table (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    valueFrom INTEGER,
                    valueTo INTEGER,
                    accountFrom INTEGER,
                    accountTo INTEGER,
                    currencyFrom TEXT,
                    currencyTo TEXT,
                    category INTEGER NOT NULL,
                    deleted BOOLEAN NOT NULL DEFAULT 0,
                    FOREIGN KEY (category) REFERENCES category_table(id),
                    FOREIGN KEY (accountFrom) REFERENCES account_table(id),
                    FOREIGN KEY (accountTo) REFERENCES account_table(id)
                )
            """)

        database.endTransaction()
    }
}
