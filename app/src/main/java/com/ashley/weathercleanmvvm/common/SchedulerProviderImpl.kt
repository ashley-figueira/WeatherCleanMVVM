package com.ashley.weathercleanmvvm.common

import com.ashley.domain.common.SchedulerProvider
import com.ashley.domain.di.PerApplication
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@PerApplication
class SchedulerProviderImpl @Inject constructor() : SchedulerProvider {

    override fun io(): Scheduler = Schedulers.io()

    override fun computation(): Scheduler = Schedulers.computation()

    override fun ui(): Scheduler = AndroidSchedulers.mainThread()
}