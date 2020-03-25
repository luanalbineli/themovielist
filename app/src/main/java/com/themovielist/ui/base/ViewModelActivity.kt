package com.themovielist.ui.base

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.themovielist.di.ViewModelFactory
import com.themovielist.extension.viewModelProvider

abstract class ViewModelActivity<VM: ViewModel>: AppCompatActivity() {
    abstract val viewModelClass: Class<VM>

    abstract val viewModelFactory: ViewModelFactory<VM>

    val viewModel: VM by lazy { viewModelProvider(viewModelClass, viewModelFactory) }
}