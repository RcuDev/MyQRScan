package com.rcudev.myqrscan.view.qrList

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rcudev.myqrscan.base.TaskState
import com.rcudev.myqrscan.data.local.model.QRItem
import com.rcudev.myqrscan.domain.usecase.DeleteQRUseCase
import com.rcudev.myqrscan.domain.usecase.GetQRListUseCase
import com.rcudev.myqrscan.domain.usecase.SaveQRUseCase
import com.rcudev.myqrscan.domain.usecase.UpdateQRUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QRListViewModel @Inject constructor(
    private val getQRListUseCase: GetQRListUseCase,
    private val saveQRUseCase: SaveQRUseCase,
    private val updateUseCase: UpdateQRUseCase,
    private val deleteQRUseCase: DeleteQRUseCase
) : ViewModel() {

    private val _state = mutableStateOf(QRListState())
    val state: State<QRListState> = _state

    init {
        viewModelScope.launch {
            getQRList()
        }
    }

    private fun getQRList() {
        getQRListUseCase().onEach { result ->
            when (result) {
                is TaskState.Success -> {
                    _state.value = QRListState(qrList = result.data ?: emptyList())
                }
                is TaskState.Error -> {
                    _state.value = QRListState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is TaskState.Loading -> {
                    _state.value = QRListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun checkResult(result: TaskState<List<QRItem>>) {
        when (result) {
            is TaskState.Success -> {
                _state.value = QRListState(qrList = result.data ?: emptyList())
            }
            is TaskState.Error -> {
                _state.value = QRListState(
                    error = result.message ?: "An unexpected error occured"
                )
            }
            is TaskState.Loading -> {
                _state.value = QRListState(isLoading = true)
            }
        }
    }

    fun saveQR(qrToSave: QRItem) {
        saveQRUseCase(qrToSave).onEach { result ->
            checkResult(result = result)
        }.launchIn(viewModelScope)
    }

    fun updateQR(qrToUpdate: QRItem) {
        updateUseCase(qrToUpdate).onEach { result ->
            checkResult(result = result)
        }.launchIn(viewModelScope)
    }

    fun deleteQR(qrToDelete: QRItem) {
        deleteQRUseCase(qrToDelete).onEach { result ->
            checkResult(result = result)
        }.launchIn(viewModelScope)
    }

}