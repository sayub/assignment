package com.assignment.android.ui

sealed class ScreenState {

    data object Empty : ScreenState()
    data object Loading : ScreenState()
    class Error(val code: Int? = null, val message: String, val body: String? = null) : ScreenState()
    data class Success(val data: Any?): ScreenState()
}