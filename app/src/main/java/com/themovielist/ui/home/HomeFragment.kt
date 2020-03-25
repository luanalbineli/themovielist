package com.themovielist.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.themovielist.R
import com.themovielist.di.module.ApiConfigurationFactory
import com.themovielist.extension.injector
import javax.inject.Inject

class HomeFragment: Fragment() {
    @Inject lateinit var apiConfigurationFactory: ApiConfigurationFactory

    private val viewModel: HomeViewModel by activityViewModels(factoryProducer = { injector.homeViewModelFactory() })

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
        = inflater.inflate(R.layout.list_view, container, false)
}