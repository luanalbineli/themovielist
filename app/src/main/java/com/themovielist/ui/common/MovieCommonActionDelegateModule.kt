package com.themovielist.ui.common

import com.themovielist.domain.FavoriteMovieUseCase
import dagger.Module
import dagger.Provides

@Module
class MovieCommonActionDelegateModule {

    @Provides
    fun provideEventActionsViewModelDelegate(favoriteMovieUseCase: FavoriteMovieUseCase): MovieCommonAction {
        return MovieCommonActionDelegate(favoriteMovieUseCase)
    }
}