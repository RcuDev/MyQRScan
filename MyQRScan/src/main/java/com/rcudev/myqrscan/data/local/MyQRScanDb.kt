package com.rcudev.myqrscan.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.rcudev.myqrscan.data.local.model.QRCategory
import com.rcudev.myqrscan.data.local.model.QRItem

@Database(
    version = 2,
    entities = [QRItem::class, QRCategory::class]
)
abstract class MyQRScanDb: RoomDatabase() {
    abstract fun composeQRDao(): MyQRScanDao
}