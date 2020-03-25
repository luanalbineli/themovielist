package com.themovielist.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = MovieEntityModel.TABLE_NAME)
data class MovieEntityModel constructor(@PrimaryKey(autoGenerate = false)
                                        @ColumnInfo(name = "id")
                                        val id: Int,

                                        @ColumnInfo(name = "poster_path")
                                        val posterPath: String?,

                                        @ColumnInfo(name = "overview")
                                        val overview: String,

                                        @ColumnInfo(name = "title")
                                        val title: String,

                                        @ColumnInfo(name = "vote_average")
                                        val voteAverage: Double,

                                        @ColumnInfo(name = "release_date")
                                        val releaseDate: Date? = null,

                                        @ColumnInfo(name = "backdrop_path")
                                        val backdropPath: String?,

                                        @ColumnInfo(name = "genre_id_list")
                                        val genreIdList: IntArray,

                                        @ColumnInfo(name = "is_favorite")
                                        val isFavorite: Boolean,

                                        @ColumnInfo(name = "is_watched")
                                        var isWatched: Boolean) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MovieEntityModel

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }

    companion object {
        const val TABLE_NAME = "movie"
    }
}
