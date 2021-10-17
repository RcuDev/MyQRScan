package com.rcudev.myqrscan.data.local

import androidx.room.*
import androidx.room.OnConflictStrategy.ABORT
import androidx.room.OnConflictStrategy.REPLACE
import com.rcudev.myqrscan.data.local.model.QRCategory
import com.rcudev.myqrscan.data.local.model.QRItem

@Dao
interface MyQRScanDao {

    /**
     * QR Items
     */

    @Update(onConflict = REPLACE)
    fun updateQRItem(qrItem: QRItem)

    @Insert(onConflict = REPLACE)
    fun saveNewQR(qrItem: QRItem)

    @Delete
    fun deleteQR(qrItem: QRItem)

    @Query("SELECT * FROM QRItem WHERE category LIKE :category")
    fun getQRListByCategory(category: String): List<QRItem>

    /**
     * QR Category
     */

    @Query("SELECT * FROM QRCategory")
    fun getQRCategoryList(): List<QRCategory>

    @Insert(onConflict = ABORT)
    fun saveNewQRCategory(qrCategory: QRCategory)

    @Delete
    fun deleteQR(qrCategory: QRCategory)
}