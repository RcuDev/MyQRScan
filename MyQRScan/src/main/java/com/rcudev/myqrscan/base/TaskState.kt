package com.rcudev.myqrscan.base

sealed class TaskState<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : TaskState<T>(data)
    class Error<T>(message: String, data: T? = null) : TaskState<T>(data, message)
    class Loading<T>(data: T? = null) : TaskState<T>(data)
}
