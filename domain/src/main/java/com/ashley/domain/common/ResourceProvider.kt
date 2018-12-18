package com.ashley.domain.common

interface ResourceProvider {
    fun getString(resId: Int): String
    fun getString(resId: Int, vararg formatArgs: Any): String

    fun getColor(resId: Int): Int
    fun getColor(colour: String?, defaultResId: Int): Int

    fun getQuantityString(resId: Int, quantity: Int): String
    fun getQuantityString(resId: Int, quantity: Int, vararg formatArgs: Any): String
}