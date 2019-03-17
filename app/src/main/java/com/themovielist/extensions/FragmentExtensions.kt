package com.themovielist.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.themovielist.di.DaggerComponentProvider
import com.themovielist.di.ViewModelFactory
import com.themovielist.ui.base.BaseViewModel

val Fragment.injector get() = (requireActivity().application as DaggerComponentProvider).component

fun <VM: BaseViewModel> Fragment.activityViewModelProvider(clazz: Class<VM>, viewModelFactory: ViewModelFactory<VM>)
        = ViewModelProviders.of(requireActivity(), viewModelFactory).get(clazz)

fun <VM: BaseViewModel> Fragment.fragmentViewModelProvider(clazz: Class<VM>, viewModelFactory: ViewModelFactory<VM>)
        = ViewModelProviders.of(this, viewModelFactory).get(clazz)