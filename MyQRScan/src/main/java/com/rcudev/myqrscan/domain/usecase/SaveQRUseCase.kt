package com.rcudev.myqrscan.domain.usecase

import com.rcudev.myqrscan.base.Resource
import com.rcudev.myqrscan.data.local.model.QRItem
import com.rcudev.myqrscan.domain.repository.MyQRScanRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SaveQRUseCase(
    private val scanRepository: MyQRScanRepository
) {
    operator fun invoke(qrToSave: QRItem): Flow<Resource<List<QRItem>>> = flow {
        try {
            emit(Resource.Loading())
            val saveSuccess = scanRepository.saveNewQR(qrToSave)
            emit(Resource.Success(saveSuccess))
        } catch (e: Exception) {
            emit(Resource.Error<List<QRItem>>(e.localizedMessage ?: "An unexpected error occured"))
        }
    }
}