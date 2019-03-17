package com.themovielist.di

import com.themovielist.ui.common.MovieCommonAction
import com.themovielist.ui.common.MovieCommonActionDelegate
import dagger.Binds
import dagger.Module

@Module
abstract class BindindModule {
    @Binds
    abstract fun bindMovieCommonActions(movieCommonActionDelegate: MovieCommonActionDelegate): MovieCommonAction
}