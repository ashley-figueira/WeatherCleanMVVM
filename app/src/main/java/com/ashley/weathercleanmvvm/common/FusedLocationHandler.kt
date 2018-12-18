package com.ashley.weathercleanmvvm.common

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.ashley.domain.di.ApplicationContext
import com.ashley.domain.di.PerApplication
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import io.reactivex.functions.Consumer
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest
import javax.inject.Inject

@PerApplication
class FusedLocationHandler @Inject constructor(@ApplicationContext private val context: Context) {

    private var fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    fun getUsersCurrentLocation(listener: Consumer<Location>) {
        if (EasyPermissions.hasPermissions(context, *locationPermissions())) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                listener.accept(location)
            }
        } else throw WeatherExceptions.LocationRequestNotGrantedException()
    }

    fun locationPermissions() : Array<String> {
        return arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
    }

    companion object {
        const val RC_LOCATION = 1
    }
}
