package com.example.sneakership.api

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val errorType: ErrorType, val message: String?) : Resource<Nothing>()

    enum class ErrorType {
        INVALID_PARAMETER, UNEXPECTED, UNKNOWN
    }
}