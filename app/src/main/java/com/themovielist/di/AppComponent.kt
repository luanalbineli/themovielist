package com.themovielist.di

import android.content.Context
import com.themovielist.di.module.AppModule
import com.themovielist.ui.MainViewModel
import com.themovielist.ui.home.HomeViewModel
import com.themovielist.ui.home.fulllist.FullMovieListViewModel
import com.themovielist.ui.movieDetail.MovieDetailViewModel
import com.themovielist.ui.movieList.MovieListViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class
])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun applicationContext(applicationContext: Context): Builder

        fun build(): AppComponent
    }

    fun mainViewModelFactory(): ViewModelFactory<MainViewModel>
    fun homeViewModelFactory(): ViewModelFactory<HomeViewModel>
    fun movieDetailViewModelFactory(): ViewModelFactory<MovieDetailViewModel>
    fun fullMovieListViewModelFactory(): ViewModelFactory<FullMovieListViewModel>
    fun movieListViewModelFactory(): ViewModelFactory<MovieListViewModel>
}