package com.ashley.weathercleanmvvm.base

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<N : BaseNavigator> : ViewModel(), DefaultLifecycleObserver {

    lateinit var mNavigator: N

    val navigator : BaseNavigator
        get() = mNavigator

    fun setLifeCycleOwner(owner : LifecycleOwner) {
        owner.lifecycle.addObserver(this)
    }

    fun setNavigator(navigator : N) {
        mNavigator = navigator
    }

}