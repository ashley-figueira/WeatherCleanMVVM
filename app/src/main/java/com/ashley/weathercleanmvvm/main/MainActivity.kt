package com.ashley.weathercleanmvvm.main

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.lifecycle.Observer
import com.ashley.weathercleanmvvm.BR
import com.ashley.weathercleanmvvm.R
import com.ashley.weathercleanmvvm.base.BaseActivity
import com.ashley.weathercleanmvvm.base.ScreenState
import com.ashley.weathercleanmvvm.common.FusedLocationHandler.Companion.RC_LOCATION
import com.ashley.weathercleanmvvm.common.getFormattedDate
import com.ashley.weathercleanmvvm.common.getStringRes
import com.ashley.weathercleanmvvm.common.visible
import com.ashley.weathercleanmvvm.common.visibleUnless
import com.ashley.weathercleanmvvm.databinding.ActivityMainBinding
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_weather.*
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), MainNavigator, EasyPermissions.PermissionCallbacks {

    @Inject lateinit var vm: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        getViewModel().screenState.observe(this, Observer { screenState ->
            when (screenState) {
                is ScreenState.LoadingState -> progressLayout.visibleUnless(screenState.isLoading)
                is ScreenState.NoWifiState -> noWifiLayout.visible()
                is ScreenState.EmptyState -> emptyLayout.visible()
                is ScreenState.HasData -> { weatherCardView.visible()
                    weatherTemp.text = screenState.data.temperature.toString()
                    weatherCondition.text = screenState.data.condition
                    weatherWind.text = "${screenState.data.wind} m/s"
                    weatherCity.text = screenState.data.city
                    weatherWindDirection.text = getString(screenState.data.windDirection.getStringRes())
                    screenState.data.lastUpdatedAt?.let { weatherUpdatedOn.text = it.getFormattedDate() }
                    Glide.with(this).load(screenState.data.iconUrl).into(weatherIcon)
                }
            }
        })

        noWifiLayout.setOnClickListener { getViewModel().onConnectToWifi() }
    }

    override fun getViewModel(): MainViewModel = vm

    override fun getLayoutResId(): Int = R.layout.activity_main

    override fun getBindingVariableId(): Int? = BR.viewModel

    override fun goToWifiSettings() = startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))

    override fun requestPermissions(permissions: Array<String>) = EasyPermissions.requestPermissions(this,
            getString(R.string.main_screen_location_permission_message), RC_LOCATION, *permissions)

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //forward permissions to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) { }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) = getViewModel().loadWeatherData()

}