package com.themovielist.di

import androidx.lifecycle.ViewModel
import com.google.samples.apps.iosched.shared.di.ViewModelKey
import com.themovielist.ui.MainActivity
import com.themovielist.ui.common.MovieCommonActionDelegateModule
import com.themovielist.ui.home.HomeModule
import com.themovielist.ui.home.HomeViewModel
import com.themovielist.ui.moviedetail.MovieDetailActivity
import com.themovielist.ui.moviedetail.MovieDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = [
        HomeModule::class,
        MovieCommonActionDelegateModule::class
    ])
    internal abstract fun mainActivity(): MainActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [
        MovieCommonActionDelegateModule::class
    ])
    internal abstract fun movieDetailActivity(): MovieDetailActivity

    /**
     * The ViewModels are created by Dagger in a map. Via the @ViewModelKey, we define that we
     * want to get a [HomeViewModel] class.
     */
    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailViewModel::class)
    abstract fun bindMovieDetailViewModel(viewModel: MovieDetailViewModel): ViewModel
}
