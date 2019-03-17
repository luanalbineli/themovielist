package com.themovielist.ui.base

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.themovielist.di.AppComponent
import com.themovielist.extensions.injector

abstract class BaseViewModelFragment<VM: ViewModel>: Fragment() {
    protected lateinit var viewModel: VM

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = instantiateViewModel(injector)
    }

    abstract fun instantiateViewModel(injector: AppComponent): VM
}