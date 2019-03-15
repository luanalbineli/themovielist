package com.themovielist.ui.home

import com.themovielist.ui.home.partiallist.HomePartialListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class HomeChildFragmentModule {
    @ChildFragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeHomePartialListFragment(): HomePartialListFragment
}