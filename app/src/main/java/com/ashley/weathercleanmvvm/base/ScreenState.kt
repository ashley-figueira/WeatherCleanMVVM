package com.ashley.weathercleanmvvm.base

sealed class ScreenState<T> {

    class NoInternet<T> : ScreenState<T>()
    data class Loading<T>(val isLoading: Boolean) : ScreenState<T>()
    data class Error<T>(val errorMessage: Int) : ScreenState<T>()
    data class Success<T>(var data: T) : ScreenState<T>()

    companion object {
        fun <T> noInternet(): ScreenState<T> = NoInternet()

        fun <T> loading(isLoading: Boolean): ScreenState<T> = Loading(isLoading)

        fun <T> error(errorMessage: Int): ScreenState<T> = Error(errorMessage)

        fun <T> success(data: T): ScreenState<T> = Success(data)
    }
}