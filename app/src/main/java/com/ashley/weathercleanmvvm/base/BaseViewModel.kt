package com.ashley.weathercleanmvvm.base

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel<N : BaseNavigator> : ViewModel(), DefaultLifecycleObserver {

    private val disposable: CompositeDisposable = CompositeDisposable()

    lateinit var mNavigator: N

    val navigator : BaseNavigator
        get() = mNavigator

    fun setLifeCycleOwner(owner : LifecycleOwner) {
        owner.lifecycle.addObserver(this)
    }

    fun setNavigator(navigator : N) {
        mNavigator = navigator
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun Disposable.addToComposite(): Disposable {
        disposable.add(this)
        return this
    }

}