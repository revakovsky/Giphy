package com.revakovskyi.domain.util

sealed class DataResult<T>(val data: T? = null, val message: String? = null) {

    class Success<T>(data: T?) : DataResult<T>(data)
    class Error<T>(data: T?, message: String) : DataResult<T>(data, message)

}