package com.themovielist.di

import android.content.Context
import com.themovielist.MainApplication
import com.themovielist.ui.MainActivityViewModel
import com.themovielist.ui.home.HomeViewModel
import com.themovielist.ui.home.fulllist.FullHomeListViewModel
import com.themovielist.ui.moviedetail.MovieDetailViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    BindindModule::class
])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun applicationContext(applicationContext: Context): Builder

        fun build(): AppComponent
    }

    fun inject(mainApplication: MainApplication)

    fun homeViewModelFactory(): ViewModelFactory<HomeViewModel>
    fun fullHomeListViewModel(): ViewModelFactory<FullHomeListViewModel>
    fun mainActivityViewModelFactory(): ViewModelFactory<MainActivityViewModel>
    fun movieDetailViewModelFactory(): ViewModelFactory<MovieDetailViewModel>
}