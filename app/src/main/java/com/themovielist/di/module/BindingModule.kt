package com.themovielist.di.module

import com.themovielist.ui.base.IMovieActions
import com.themovielist.ui.base.MovieActionsImp
import dagger.Binds
import dagger.Module

@Module
abstract class BindingModule {
    @Binds
    abstract fun bindMovieActions(movieActionsImp: MovieActionsImp): IMovieActions
}