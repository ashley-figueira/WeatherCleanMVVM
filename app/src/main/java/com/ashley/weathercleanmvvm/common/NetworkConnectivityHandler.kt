package com.ashley.weathercleanmvvm.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.ashley.domain.di.ApplicationContext
import com.ashley.domain.di.PerApplication
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

@PerApplication
class NetworkConnectivityHandler @Inject constructor(@ApplicationContext context: Context) : ConnectivityManager.NetworkCallback() {

    private val networkPublisher : PublishSubject<Boolean> = PublishSubject.create()
    private val connectivityManager : ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    init {
        val request = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .addCapability(NetworkCapabilities.NET_CAPABILITY_NOT_RESTRICTED)
                .build()
        connectivityManager.registerNetworkCallback(request, this)
    }

    fun hasNetworkConnectivity(): Boolean {
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    fun listen(): Observable<Boolean> = networkPublisher

    override fun onAvailable(network: Network?) {
        super.onAvailable(network)
        networkPublisher.onNext(true)
    }

    override fun onLost(network: Network?) {
        super.onLost(network)
        networkPublisher.onNext(false)
    }
}