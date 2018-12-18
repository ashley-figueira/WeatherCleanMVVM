package com.ashley.domain.common

sealed class WError(val throwable: Throwable) {

    class Offline(throwable: Throwable) : WError(throwable)

    class Timeout(throwable: Throwable) : WError(throwable)

    class Unknown(throwable: Throwable) : WError(throwable)

    class NoWeatherInDatabase(throwable: Throwable) : WError(throwable)
}