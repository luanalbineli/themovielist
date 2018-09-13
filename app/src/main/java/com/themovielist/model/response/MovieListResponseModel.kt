package com.themovielist.model.response

import com.themovielist.model.view.MovieImageGenreViewModel


data class MovieListResponseModel(val configurationResponseModel: ConfigurationResponseModel,
                                  var movieWithGenreList: List<MovieImageGenreViewModel>)