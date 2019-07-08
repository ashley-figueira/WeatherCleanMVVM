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

fun Completable.compose(schedulerProvider: SchedulerProvider): Completable {
    return this.subscribeOn(schedulerProvider.io()).observeOn(schedulerProvider.ui())
}

fun <T> Maybe<T>.compose(schedulerProvider: SchedulerProvider): Maybe<T> {
    return this.subscribeOn(schedulerProvider.io()).observeOn(schedulerProvider.ui())
}

fun <T> Observable<T>.compose(schedulerProvider: SchedulerProvider): Observable<T> {
    return this.subscribeOn(schedulerProvider.io()).observeOn(schedulerProvider.ui())
}

fun <T> Flowable<T>.compose(schedulerProvider: SchedulerProvider): Flowable<T> {
    return this.subscribeOn(schedulerProvider.io()).observeOn(schedulerProvider.ui())
}

fun <T> Single<WResult<T>>.doOnWeatherSuccess(onSuccess: ((T) -> Unit)) =
    doOnSuccess { if (it is WResult.Success) onSuccess.invoke(it.data) }

fun <T> Single<WResult<T>>.subscribeWeatherResult(onSuccess: ((T) -> Unit), onError: ((WError) -> Unit)) =
    subscribeWith(object : DisposableSingleObserver<WResult<T>>() {
        override fun onSuccess(result: WResult<T>) {
            when (result) {
                is WResult.Success -> onSuccess.invoke(result.data)
                is WResult.Failure -> onError.invoke(result.error)
            }
        }

        override fun onError(e: Throwable) {
            onError.invoke(WError.Unknown(e))
        }
    })

fun <T> Observable<WResult<T>>.subscribeWeatherResult(onSuccess: ((T) -> Unit), onError: ((WError) -> Unit)) =
    subscribeWith(object : DisposableObserver<WResult<T>>() {
        override fun onComplete() {
            //Do nothing
        }

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