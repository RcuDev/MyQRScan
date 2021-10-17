package com.rcudev.myqrscan.domain.repository

import com.rcudev.myqrscan.data.local.model.QRCategory
import com.rcudev.myqrscan.data.local.model.QRItem

interface MyQRScanRepository {
    suspend fun getQRListByCategory(category: String): List<QRItem>
    suspend fun saveNewQR(qrToSave: QRItem): List<QRItem>
    suspend fun updateQR(qrToUpdate: QRItem): List<QRItem>
    suspend fun deleteQR(qrToDelete: QRItem): List<QRItem>
    suspend fun saveQRCategory(categoryToSave: QRCategory): List<QRCategory>
    suspend fun deleteQRCategory(categoryToDelete: QRCategory): List<QRCategory>
}