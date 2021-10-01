package com.rcudev.myqrscan.data

import com.rcudev.myqrscan.data.local.MyQRScanDao
import com.rcudev.myqrscan.data.local.model.QRItem
import com.rcudev.myqrscan.domain.repository.MyQRScanRepository
import javax.inject.Inject

class MyQRScanScanRepositoryImpl @Inject constructor(private val myQRScanDao: MyQRScanDao) :
    MyQRScanRepository {

    override suspend fun getQRList() = listOf(QRItem(1, "Google", "https://google.com"), QRItem(2, "Firebase", ""))

    override suspend fun saveNewQR(qrToSave: QRItem): List<QRItem> {
        myQRScanDao.saveNewQR(qrItem = qrToSave)
        return myQRScanDao.getQRItemList()
    }

    override suspend fun updateQR(qrToUpdate: QRItem): List<QRItem> {
        myQRScanDao.updateQRItem(qrItem = qrToUpdate)
        return myQRScanDao.getQRItemList()
    }

    override suspend fun deleteQR(qrToDelete: QRItem): List<QRItem> {
        myQRScanDao.deleteQR(qrItem = qrToDelete)
        return myQRScanDao.getQRItemList()
    }

}