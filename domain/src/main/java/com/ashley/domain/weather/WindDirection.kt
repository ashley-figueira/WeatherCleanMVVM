package com.ashley.domain.weather

sealed class WindDirection {
    object North : WindDirection()
    object NorthEast : WindDirection()
    object East : WindDirection()
    object SouthEast : WindDirection()
    object South : WindDirection()
    object SouthWest : WindDirection()
    object West : WindDirection()
    object NorthWest : WindDirection()
}