package com.ashley.weathercleanmvvm.common

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat
import com.ashley.domain.common.ResourceProvider
import com.ashley.domain.di.PerApplication
import javax.inject.Inject

@PerApplication
class ResourceProviderImpl @Inject constructor(context: Context) : ResourceProvider {
    private var resources: Resources = context.resources

    override fun getString(@StringRes resId: Int): String = resources.getString(resId)

    override fun getString(@StringRes resId: Int, vararg formatArgs: Any): String = resources.getString(resId, *formatArgs)

    override fun getColor(@ColorRes resId: Int): Int = ResourcesCompat.getColor(resources, resId, null)

    override fun getColor(colour: String?, @ColorRes defaultResId: Int): Int {
        return try {
            if (colour != null) Color.parseColor(colour) else getColor(defaultResId)
        } catch (e: Exception) {
            getColor(defaultResId)
        }
    }

    override fun getQuantityString(resId: Int, quantity: Int): String = resources.getQuantityString(resId, quantity)

    override fun getQuantityString(resId: Int, quantity: Int, vararg formatArgs: Any): String = resources.getQuantityString(resId, quantity, formatArgs)
}