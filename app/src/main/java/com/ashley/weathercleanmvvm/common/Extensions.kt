package com.ashley.weathercleanmvvm.common

import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.ashley.domain.entities.WindDirection
import com.ashley.weathercleanmvvm.GlideApp
import com.ashley.weathercleanmvvm.R
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

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

fun DateTime.getFormattedDate(): String = DateTimeFormat.forPattern(DATE_FORMAT).print(this)

private const val DATE_FORMAT = "MMMM dd yyyy HH:mm"

fun ImageView.load(url: String, @DrawableRes placeholder: Int) {
    GlideApp.with(this)
        .load(url)
        .placeholder(placeholder)
        .error(placeholder)
        .into(this)
}
