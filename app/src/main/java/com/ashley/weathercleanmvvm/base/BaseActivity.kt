package com.ashley.weathercleanmvvm.base

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import dagger.android.AndroidInjection

abstract class BaseActivity<B : ViewDataBinding, V : BaseViewModel<*>> : AppCompatActivity(), BaseNavigator {

    abstract fun getViewModel() : V?

    @LayoutRes abstract fun getLayoutResId() : Int

    abstract fun getBindingVariableId() : Int?

    private lateinit var binding : B

    fun getViewBinding() : B = binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        binding = DataBindingUtil.setContentView(this, getLayoutResId())
    }

    override fun onStart() {
        super.onStart()
        getBindingVariableId()?.let { binding.setVariable(it, getViewModel()) }
        getViewBinding().setLifecycleOwner(this)
    }

    override fun onBack() {
        onBackPressed()
    }

    override fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        currentFocus?.let {
            imm.hideSoftInputFromWindow(it.windowToken, 0)
            return
        }

        if (imm.isActive) {
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0) // hide
        }
    }
}