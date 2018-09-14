package com.themovielist.di

import com.themovielist.ui.MainActivity
import com.themovielist.ui.common.MovieCommonActionDelegateModule
import com.themovielist.ui.home.HomeModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = [
        HomeModule::class,
        MovieCommonActionDelegateModule::class
    ])
    internal abstract fun mainActivity(): MainActivity
}