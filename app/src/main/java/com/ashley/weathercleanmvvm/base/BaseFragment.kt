package com.ashley.weathercleanmvvm.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

abstract class BaseFragment<B : ViewDataBinding, V : BaseViewModel<*>> : DaggerFragment() {

    protected lateinit var disposables: CompositeDisposable

    @Inject lateinit var viewModelProvider: ViewModelProvider.Factory

    protected lateinit var viewModel: V

    protected lateinit var binding : B

    @LayoutRes abstract fun getLayoutResId() : Int

    abstract fun getBindingVariableId() : Int?

    fun getViewBinding() : B = binding

    override fun onCreate(savedInstanceState: Bundle?) {
        disposables = CompositeDisposable()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        initUi()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModelClass = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<V>
        viewModel = ViewModelProviders.of(this, viewModelProvider).get(viewModelClass)
        viewModel.setLifeCycleOwner(this)
        getBindingVariableId()?.let { binding.setVariable(it, viewModel) }
    }

    abstract fun initUi()

    override fun onDestroyView() {
        disposables.clear()
        super.onDestroyView()
    }

    fun Disposable.addToComposite(): Disposable {
        disposables.add(this)
        return this
    }

    fun showErrorAsSnackbar(@StringRes messageId: Int) { view?.let { Snackbar.make(it, messageId, Snackbar.LENGTH_SHORT).show() } }

    fun navigateTo(navDirections: NavDirections) = findNavController().navigate(navDirections)

    fun navigateUp() = findNavController().navigateUp()
}