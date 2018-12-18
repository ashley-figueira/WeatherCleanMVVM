package com.ashley.domain.common

sealed class WResult<T> {

    class Success<T>(val data: T) : WResult<T>() {
        override fun equals(other: Any?): Boolean {
            return other is Success<*> && other.hashCode() == hashCode()
        }

        override fun hashCode(): Int {
            return data?.hashCode() ?: 0
        }
    }

    class Failure<T>(val error: WError) : WResult<T>()
}