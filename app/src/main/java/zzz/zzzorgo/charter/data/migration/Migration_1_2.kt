package zzz.zzzorgo.charter.data.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE record_table ADD COLUMN date TEXT NOT NULL DEFAULT '2020-05-20T23:38:00.000'")
    }
}
