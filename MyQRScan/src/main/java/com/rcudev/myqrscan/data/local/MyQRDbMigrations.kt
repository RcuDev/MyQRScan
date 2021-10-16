package com.rcudev.myqrscan.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object MyQRDbMigrations {
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("CREATE TABLE `QRCategory` (`categoryName` TEXT PRIMARY KEY)")
            database.execSQL("ALTER TABLE `QRItem` ADD COLUMN `category` TEXT NOT NULL DEFAULT 'Recent'")
        }
    }
}