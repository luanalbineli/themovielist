package com.themovielist.repository.favorite

import androidx.room.*
import com.themovielist.model.entity.MovieEntityModel
import com.themovielist.model.entity.MovieFavoriteWatchedEntityModel

@Dao
interface MovieDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieEntityModel: MovieEntityModel)

    @Delete
    suspend fun delete(movieEntityModel: MovieEntityModel)

    @Query("SELECT id, is_favorite, is_watched from ${MovieEntityModel.TABLE_NAME}")
    suspend fun getFavoriteWatchedMovieList(): List<MovieFavoriteWatchedEntityModel>
}