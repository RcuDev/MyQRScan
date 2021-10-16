package com.rcudev.myqrscan.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rcudev.myqrscan.data.local.model.QRItem

@Database(
    entities = [QRItem::class],
    version = 2
)
abstract class MyQRScanDb: RoomDatabase() {
    abstract fun composeQRDao(): MyQRScanDao
}