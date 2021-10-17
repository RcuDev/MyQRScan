package com.rcudev.myqrscan.data

import com.rcudev.myqrscan.data.local.MyQRScanDao
import com.rcudev.myqrscan.data.local.model.QRCategory
import com.rcudev.myqrscan.data.local.model.QRItem
import com.rcudev.myqrscan.domain.repository.MyQRScanRepository
import javax.inject.Inject

class MyQRScanScanRepositoryImpl @Inject constructor(private val myQRScanDao: MyQRScanDao) :
    MyQRScanRepository {

    /**
     * QR Items
     */

    override suspend fun getQRListByCategory(category: String) =
        myQRScanDao.getQRListByCategory(category)

    override suspend fun saveNewQR(qrToSave: QRItem): List<QRItem> {
        myQRScanDao.saveNewQR(qrToSave)
        return myQRScanDao.getQRListByCategory(qrToSave.category)
    }

    override suspend fun updateQR(qrToUpdate: QRItem): List<QRItem> {
        myQRScanDao.updateQRItem(qrToUpdate)
        return myQRScanDao.getQRListByCategory(qrToUpdate.category)
    }

    override suspend fun deleteQR(qrToDelete: QRItem): List<QRItem> {
        val category: String = qrToDelete.category
        myQRScanDao.deleteQR(qrToDelete)
        return myQRScanDao.getQRListByCategory(category)
    }

    /**
     * QR Category
     */

    override suspend fun saveQRCategory(categoryToSave: QRCategory): List<QRCategory> {
        myQRScanDao.saveNewQRCategory(categoryToSave)
        return myQRScanDao.getQRCategoryList()
    }

    override suspend fun deleteQRCategory(
        categoryToDelete: QRCategory,
        categoryToMoveAllQR: QRCategory
    ): List<QRCategory> {
        myQRScanDao.deleteQRCategory(categoryToDelete)
        myQRScanDao.moveQRToRecentCategory(
            categoryToDelete.categoryName,
            categoryToMoveAllQR.categoryName
        )
        return myQRScanDao.getQRCategoryList()
    }

}