package com.example.sneakership.api

import retrofit2.Response
import javax.inject.Inject

class ErrorHandler @Inject constructor() {

    fun <T> handleApiResponse(response: Response<T>): Resource<T> {
        return if (response.isSuccessful) {
            val data = response.body()
            if (data != null) {
                Resource.Success(data)
            } else {
                Resource.Error(Resource.ErrorType.UNKNOWN, "Empty response")
            }
        } else {
            val errorType = getErrorType(response.code())
            val errorMessage = response.errorBody()?.string()?.takeIf { it.isNotBlank() } ?: "Unexpected error"
            Resource.Error(errorType, errorMessage)
        }
    }

    private fun getErrorType(code: Int): Resource.ErrorType = when (code) {
        400 -> Resource.ErrorType.INVALID_PARAMETER
        500 -> Resource.ErrorType.UNEXPECTED
        else -> Resource.ErrorType.UNKNOWN
    }
}
