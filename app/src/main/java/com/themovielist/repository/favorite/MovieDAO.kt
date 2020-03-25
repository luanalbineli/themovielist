package com.themovielist.repository.favorite

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.themovielist.model.entity.MovieEntityModel
import com.themovielist.model.entity.MovieFavoriteWatchedEntityModel

@Dao
interface MovieDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieModel: MovieEntityModel): Long

    /*@Query("SELECT * FROM ${MovieEntityModel.TABLE_NAME} WHERE id = :id")
    fun selectById(id: Int): Cursor?*/

    /*@Query("DELETE FROM ${MovieEntityModel.TABLE_NAME} WHERE id = :id")
    fun deleteById(id: Int): Int*/

    @Query("SELECT id, is_favorite, is_watched from ${MovieEntityModel.TABLE_NAME}")
    suspend fun getFavoriteWatchedMovieList(): List<MovieFavoriteWatchedEntityModel>
}