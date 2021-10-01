package com.rcudev.myqrscan.domain.usecase

import com.rcudev.myqrscan.base.TaskState
import com.rcudev.myqrscan.data.local.model.QRItem
import com.rcudev.myqrscan.domain.repository.MyQRScanRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetQRListUseCase(
    private val scanRepository: MyQRScanRepository
) {
    operator fun invoke(): Flow<TaskState<List<QRItem>>> = flow {
        try {
            emit(TaskState.Loading())
            val saveSuccess = scanRepository.getQRList()
            emit(TaskState.Success(saveSuccess))
        } catch (e: Exception) {
            emit(TaskState.Error<List<QRItem>>(e.localizedMessage ?: "An unexpected error occured"))
        }
    }
}