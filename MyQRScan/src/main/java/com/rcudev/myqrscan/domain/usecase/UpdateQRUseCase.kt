package com.rcudev.myqrscan.domain.usecase

import com.rcudev.myqrscan.base.TaskState
import com.rcudev.myqrscan.data.local.model.QRItem
import com.rcudev.myqrscan.domain.repository.MyQRScanRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdateQRUseCase(
    private val scanRepository: MyQRScanRepository
) {
    operator fun invoke(qrToUpdate: QRItem): Flow<TaskState<List<QRItem>>> = flow {
        try {
            emit(TaskState.Loading())
            val saveSuccess = scanRepository.updateQR(qrToUpdate)
            emit(TaskState.Success(saveSuccess))
        } catch (e: Exception) {
            emit(TaskState.Error<List<QRItem>>(e.localizedMessage ?: "An unexpected error occured"))
        }
    }
}