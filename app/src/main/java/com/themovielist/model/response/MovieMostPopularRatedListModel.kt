package com.themovielist.model.response

import com.themovielist.model.view.CompleteMovieModel

data class MovieMostPopularRatedListModel(val mostPopularMovieList: List<CompleteMovieModel>, val topRatedMovieList: List<CompleteMovieModel>)