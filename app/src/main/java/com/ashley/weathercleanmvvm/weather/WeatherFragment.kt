package com.ashley.weathercleanmvvm.weather

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.lifecycle.Observer
import com.ashley.weathercleanmvvm.BR
import com.ashley.weathercleanmvvm.R
import com.ashley.weathercleanmvvm.base.BaseFragment
import com.ashley.weathercleanmvvm.base.ScreenAction
import com.ashley.weathercleanmvvm.base.ScreenState
import com.ashley.weathercleanmvvm.common.*
import com.ashley.weathercleanmvvm.databinding.FragmentWeatherBinding
import com.bumptech.glide.Glide
import com.google.android.gms.location.LocationServices
import com.jakewharton.rxrelay2.PublishRelay
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.fragment_weather.*
import kotlinx.android.synthetic.main.item_weather.*

class WeatherFragment : BaseFragment<FragmentWeatherBinding, WeatherViewModel>() {

    private val actionStream: PublishRelay<ScreenAction> = PublishRelay.create()
    private val fusedLocationClient by lazy { LocationServices.getFusedLocationProviderClient(requireContext()) }

    override fun getLayoutResId(): Int = R.layout.fragment_weather

    override fun getBindingVariableId(): Int? = BR.vm

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (actionStream.hasObservers().not()) viewModel.attach(actionStream)
        viewModel.screenState.observe(viewLifecycleOwner, Observer { screenState ->
            when (screenState) {
                is ScreenState.Loading -> refreshLayout.isRefreshing = screenState.isLoading
                is ScreenState.Error -> {
                    emptyLayout.visible()
                    noWifiLayout.gone()
                    weatherCardView.gone()
                }
                is ScreenState.NoInternet -> {
                    emptyLayout.gone()
                    noWifiLayout.visible()
                    weatherCardView.gone()
                }
                is ScreenState.Success -> {
                    emptyLayout.gone()
                    noWifiLayout.gone()
                    weatherCardView.visible()

                    weatherTemp.text = screenState.data.temperature.toString()
                    weatherCondition.text = screenState.data.condition
                    weatherWind.text = "${screenState.data.wind} m/s"
                    weatherCity.text = screenState.data.city
                    weatherWindDirection.text = getString(screenState.data.windDirection.getStringRes())
                    weatherUpdatedOn.text = screenState.data.lastUpdatedAt.getFormattedDate()
                    Glide.with(this).load(screenState.data.iconUrl).into(weatherIcon)

                }
            }
        })
    }

    override fun initUi() {
        binding.refreshLayout.setOnRefreshListener { actionStream.accept(ScreenAction.PullToRefreshAction) }
    }

    @SuppressLint("MissingPermission")
    override fun onStart() {
        super.onStart()
        RxPermissions(this)
            .request(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
            .subscribe { granted ->
                if (granted) {
                    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                        viewModel.loadWeather(arrayOf(location.latitude, location.longitude))
                    }
                } else {
                    emptyLayout.visible()
                    noWifiLayout.gone()
                    weatherCardView.gone()
                }
            }
            .addToComposite()
    }
}