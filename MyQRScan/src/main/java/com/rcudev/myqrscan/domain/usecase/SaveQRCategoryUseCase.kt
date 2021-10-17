package com.rcudev.myqrscan.domain.usecase

import com.rcudev.myqrscan.base.TaskState
import com.rcudev.myqrscan.data.local.model.QRCategory
import com.rcudev.myqrscan.domain.repository.MyQRScanRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SaveQRCategoryUseCase(
    private val scanRepository: MyQRScanRepository
) {
    operator fun invoke(categoryToSave: QRCategory): Flow<TaskState<List<QRCategory>>> = flow {
        try {
            emit(TaskState.Loading())
            val saveSuccess = scanRepository.saveQRCategory(categoryToSave)
            emit(TaskState.Success(saveSuccess))
        } catch (e: Exception) {
            emit(
                TaskState.Error<List<QRCategory>>(
                    e.localizedMessage ?: "An unexpected error occured"
                )
            )
        }
    }
}