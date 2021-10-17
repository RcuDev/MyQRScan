package com.rcudev.myqrscan.view.qrList

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rcudev.myqrscan.base.TaskState
import com.rcudev.myqrscan.data.local.model.QRCategory
import com.rcudev.myqrscan.data.local.model.QRItem
import com.rcudev.myqrscan.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QRListViewModel @Inject constructor(
    private val getQRListByCategoryUseCase: GetQRListByCategoryUseCase,
    private val saveQRUseCase: SaveQRUseCase,
    private val updateUseCase: UpdateQRUseCase,
    private val deleteQRUseCase: DeleteQRUseCase,
    private val saveQRCategoryUseCase: SaveQRCategoryUseCase,
    private val deleteQRCategoryUseCase: DeleteQRCategoryUseCase
) : ViewModel() {

    private val _state = mutableStateOf(QRListState())
    val state: State<QRListState> = _state

    val recentCategoryText: MutableState<String> = mutableStateOf("")
    val qrList: MutableState<List<QRItem>> = mutableStateOf(emptyList())
    val selectedCategory: MutableState<QRCategory> =
        mutableStateOf(QRCategory(recentCategoryText.value))
    val qrCategoryList: MutableState<List<QRCategory>> = mutableStateOf(emptyList())

    fun initViewModel(recentCategory: QRCategory) {
        viewModelScope.launch {
            recentCategoryText.value = recentCategory.categoryName
            selectedCategory.value = recentCategory
            saveQRCategory(recentCategory)
            getQRListByCategory(QRCategory(recentCategory.categoryName))
        }
    }

    fun saveQR(qrToSave: QRItem) {
        saveQRUseCase(qrToSave).onEach { result ->
            checkQRResult(result = result)
        }.launchIn(viewModelScope)
    }

    fun updateQR(qrToUpdate: QRItem) {
        updateUseCase(qrToUpdate).onEach { result ->
            checkQRResult(result = result)
        }.launchIn(viewModelScope)
        getQRListByCategory(selectedCategory.value, false)
    }

    fun deleteQR(qrToDelete: QRItem) {
        deleteQRUseCase(qrToDelete).onEach { result ->
            checkQRResult(result = result)
        }.launchIn(viewModelScope)
    }

    fun getQRListByCategory(qrCategory: QRCategory, updateSelectedCategory: Boolean = true) {
        if (updateSelectedCategory) {
            selectedCategory.value = qrCategory
        }

        getQRListByCategoryUseCase(qrCategory.categoryName).onEach { result ->
            checkQRResult(result = result)
        }.launchIn(viewModelScope)
    }

    fun saveQRCategory(categoryToSave: QRCategory) {
        saveQRCategoryUseCase(categoryToSave).onEach { result ->
            checkQRCategoryResult(result = result)
        }.launchIn(viewModelScope)
    }

    fun deleteQRCategory(categoryToDelete: QRCategory) {
        if (selectedCategory.value.categoryName == categoryToDelete.categoryName) {
            selectedCategory.value = QRCategory(recentCategoryText.value)
            getQRListByCategory(QRCategory(recentCategoryText.value))
        }
        deleteQRCategoryUseCase(
            categoryToDelete,
            QRCategory(recentCategoryText.value)
        ).onEach { result ->
            checkQRCategoryResult(result = result)
        }.launchIn(viewModelScope)
        getQRListByCategory(selectedCategory.value, false)
    }

    private fun checkQRResult(
        result: TaskState<List<QRItem>>
    ) {
        when (result) {
            is TaskState.Success -> {
                qrList.value = result.data ?: emptyList()
            }
            is TaskState.Error -> {
                _state.value = QRListState(
                    error = result.message ?: "An unexpected error occured"
                )
                Log.e("MYQRSCANNER", state.value.error)
            }
            is TaskState.Loading -> {
                _state.value = QRListState(isLoading = true)
            }
        }
    }

    private fun checkQRCategoryResult(
        result: TaskState<List<QRCategory>>
    ) {
        when (result) {
            is TaskState.Success -> {
                qrCategoryList.value = result.data ?: emptyList()
            }
            is TaskState.Error -> {
                _state.value = QRListState(
                    error = result.message ?: "An unexpected error occured"
                )
                Log.e("MYQRSCANNER", state.value.error)
            }
            is TaskState.Loading -> {
                _state.value = QRListState(isLoading = true)
            }
        }
    }

}