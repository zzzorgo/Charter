package zzz.zzzorgo.charter.data.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE account_table ADD COLUMN total INTEGER NOT NULL DEFAULT 0")
    }
}
