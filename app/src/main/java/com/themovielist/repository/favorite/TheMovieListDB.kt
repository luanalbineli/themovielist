package com.themovielist.repository.favorite

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.themovielist.model.entity.MovieEntityModel

@Database(entities = [
    MovieEntityModel::class
], version = 1)
@TypeConverters(RoomConverters::class)
abstract class TheMovieListDB: RoomDatabase() {
    abstract fun movieDAO() : MovieDAO
}
