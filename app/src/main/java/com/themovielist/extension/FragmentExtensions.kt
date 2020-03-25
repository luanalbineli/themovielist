package com.themovielist.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.themovielist.MainApplication
import com.themovielist.di.ViewModelFactory

val Fragment.injector get() = (requireActivity().application as MainApplication).component

fun <VM : ViewModel> Fragment.activityViewModelProvider(clazz: Class<VM>, viewModelFactory: ViewModelFactory<VM>) = ViewModelProvider(requireActivity(), viewModelFactory).get(clazz)

fun <VM : ViewModel> Fragment.fragmentViewModelProvider(clazz: Class<VM>, viewModelFactory: ViewModelFactory<VM>) = ViewModelProvider(this, viewModelFactory).get(clazz)