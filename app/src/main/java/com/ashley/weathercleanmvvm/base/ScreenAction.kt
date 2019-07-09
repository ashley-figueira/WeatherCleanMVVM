package com.ashley.weathercleanmvvm.base

sealed class ScreenAction {
    object PullToRefreshAction : ScreenAction()
}