package com.themovielist.model.entity

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class MovieFavoriteWatchedEntityModel
constructor(
        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = "id")
        val id: Int,

        @ColumnInfo(name = "is_watched")
        val isWatched: Boolean,

        @ColumnInfo(name = "is_favorite")
        val isFavorite: Boolean
)