package com.themovielist.ui.home

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class HomeModule {
    @FragmentScoped
    @ContributesAndroidInjector(modules = [
        HomeChildFragmentModule::class
    ])
    internal abstract fun contributeHomeFragment(): HomeFragment

    /**
     * The ViewModels are created by Dagger in a map. Via the @ViewModelKey, we define that we
     * want to get a [HomeViewModel] class.
     */
    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel
}