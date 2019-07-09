package com.ashley.domain.extensions

import com.ashley.domain.common.SchedulerProvider
import com.ashley.domain.common.WError
import com.ashley.domain.common.WResult
import io.reactivex.*
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver

fun <T> Single<T>.compose(schedulerProvider: SchedulerProvider): Single<T> {
    return this.subscribeOn(schedulerProvider.io()).observeOn(schedulerProvider.ui())
}

fun <T> Single<WResult<T>>.doOnWeatherSuccess(onSuccess: ((T) -> Unit)) =
    doOnSuccess { if (it is WResult.Success) onSuccess.invoke(it.data) }

fun <T> Observable<WResult<T>>.subscribeWeatherResult(onSuccess: ((T) -> Unit), onError: ((WError) -> Unit)) =
    subscribeWith(object : DisposableObserver<WResult<T>>() {
        override fun onComplete() {}

        override fun onNext(result: WResult<T>) {
            when (result) {
                is WResult.Success -> onSuccess.invoke(result.data)
                is WResult.Failure -> onError.invoke(result.error)
            }
        }

        override fun onError(e: Throwable) {
            onError.invoke(WError.Unknown(e))
        }
    })