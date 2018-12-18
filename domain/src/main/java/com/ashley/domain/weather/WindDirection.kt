package com.ashley.domain.weather

sealed class WindDirection {
    object North : WindDirection() { const val direction = "North" }
    object NorthEast : WindDirection() { const val direction = "North East" }
    object East : WindDirection() { const val direction = "East" }
    object SouthEast : WindDirection() { const val direction = "South East" }
    object South : WindDirection() { const val direction = "South" }
    object SouthWest : WindDirection() { const val direction = "South West" }
    object West : WindDirection() { const val direction = "West" }
    object NorthWest : WindDirection() { const val direction = "North West" }
}