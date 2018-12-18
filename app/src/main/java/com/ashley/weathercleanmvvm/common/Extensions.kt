package com.ashley.weathercleanmvvm.common

import android.view.View
import com.ashley.domain.weather.WindDirection
import com.ashley.weathercleanmvvm.R

fun View.gone() { this.visibility = View.GONE }

fun View.visible() { this.visibility = View.VISIBLE }

fun View.visibleUnless(isVisible: Boolean) { this.visibility = if (isVisible) View.VISIBLE else View.GONE }

fun WindDirection.getStringRes(): Int {
    return when (this) {
        WindDirection.North -> R.string.north
        WindDirection.NorthEast -> R.string.north_east
        WindDirection.East -> R.string.east
        WindDirection.SouthEast -> R.string.south_east
        WindDirection.South -> R.string.south
        WindDirection.SouthWest -> R.string.south_west
        WindDirection.West -> R.string.west
        WindDirection.NorthWest -> R.string.north_west
    }
}
