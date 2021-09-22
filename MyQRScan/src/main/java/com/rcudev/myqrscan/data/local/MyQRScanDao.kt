package com.rcudev.myqrscan.data.local

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.rcudev.myqrscan.data.local.model.QRItem

@Dao
interface MyQRScanDao {

    @Query("SELECT * FROM QRItem")
    fun getQRItemList(): List<QRItem>

    @Update(onConflict = REPLACE)
    fun updateQRItem(qrItem: QRItem)

    @Insert(onConflict = REPLACE)
    fun saveNewQR(qrItem: QRItem)

    @Delete
    fun deleteQR(qrItem: QRItem)
}