package com.themovielist.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.themovielist.di.ViewModelFactory

abstract class BaseViewModelActivity<VM: BaseViewModel>: AppCompatActivity() {
    protected lateinit var viewModel: VM

    abstract val viewModelClass: Class<VM>

    abstract val viewModelFactory: ViewModelFactory<VM>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = getViewModel(viewModelClass, viewModelFactory)
    }

    private fun <VM: BaseViewModel> getViewModel(clazz: Class<VM>, viewModelFactory: ViewModelFactory<VM>)
            = ViewModelProviders.of(this, viewModelFactory).get(clazz)
}