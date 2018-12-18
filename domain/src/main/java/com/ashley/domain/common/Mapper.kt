package com.ashley.domain.common

import io.reactivex.Observable

abstract class Mapper<in From, To> {

    abstract fun mapFrom(from: From): To

    fun mapFromList(from: List<From>): List<To> {
        return from.map { mapFrom(it) }
    }

    fun observable(from: From): Observable<To> {
        return Observable.fromCallable { mapFrom(from) }
    }

    fun observable(from: List<From>): Observable<List<To>> {
        return Observable.fromCallable { mapFromList(from) }
    }
}