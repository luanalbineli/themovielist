package com.themovielist.model

import android.content.ContentValues
import android.database.Cursor
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.themovielist.repository.favorite.MovieContract
import com.themovielist.util.extensions.getAsIntArray
import com.themovielist.util.extensions.getIntArray
import com.themovielist.util.extensions.getNullableString
import com.themovielist.util.extensions.put
import java.util.*

@Entity(tableName = MovieContract.MovieEntry.TABLE_NAME)
data class MovieModel constructor(@SerializedName("id")
                                  @PrimaryKey
                                  @ColumnInfo(name = MovieContract.MovieEntry._ID)
                                  var id: Int = 0,

                                  @SerializedName("poster_path")
                                  @ColumnInfo(name = MovieContract.MovieEntry.COLUMN_POSTER_PATH)
                                  var posterPath: String? = "",

                                  @SerializedName("overview")
                                  @ColumnInfo(name = MovieContract.MovieEntry.COLUMN_OVERVIEW)
                                  var overview: String = "",

                                  @SerializedName("title")
                                  @ColumnInfo(name = MovieContract.MovieEntry.COLUMN_TITLE)
                                  var title: String = "",

                                  @SerializedName("vote_average")
                                  @ColumnInfo(name = MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE)
                                  var voteAverage: Double = 0.0,

                                  @SerializedName("release_date")
                                  @ColumnInfo(name = MovieContract.MovieEntry.COLUMN_RELEASE_DATE)
                                  var releaseDate: Date? = null,

                                  @SerializedName("backdrop_path")
                                  @ColumnInfo(name = MovieContract.MovieEntry.COLUMN_BACKDROP_PATH)
                                  var backdropPath: String?,

                                  @SerializedName("genre_ids")
                                  @ColumnInfo(name = MovieContract.MovieEntry.COLUMN_GENRE_ID_LIST)
                                  val genreIdList: IntArray) {

    constructor(contentValues: ContentValues) : this(
            contentValues.getAsInteger(MovieContract.MovieEntry._ID),
            contentValues.getAsString(MovieContract.MovieEntry.COLUMN_POSTER_PATH),
            contentValues.getAsString(MovieContract.MovieEntry.COLUMN_OVERVIEW),
            contentValues.getAsString(MovieContract.MovieEntry.COLUMN_TITLE),
            contentValues.getAsDouble(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE),
            Date(contentValues.getAsLong(MovieContract.MovieEntry.COLUMN_RELEASE_DATE)),
            contentValues.getAsString(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH),
            contentValues.getAsIntArray(MovieContract.MovieEntry.COLUMN_GENRE_ID_LIST))

    private constructor(cursor: Cursor) : this(
            cursor.getInt(cursor.getColumnIndex(MovieContract.MovieEntry._ID)),
            cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH)),
            cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW)),
            cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE)),
            cursor.getDouble(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE)),
            Date(cursor.getLong(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE_DATE))),
            cursor.getNullableString(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH),
            cursor.getIntArray(MovieContract.MovieEntry.COLUMN_GENRE_ID_LIST))

    fun toContentValues(): ContentValues {
        val contentValues = ContentValues()
        contentValues.put(MovieContract.MovieEntry._ID, id)

        contentValues.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, posterPath)

        contentValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, overview)

        contentValues.put(MovieContract.MovieEntry.COLUMN_TITLE, title)

        contentValues.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE, voteAverage)

        contentValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, releaseDate?.time)

        contentValues.put(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH, backdropPath)

        contentValues.put(MovieContract.MovieEntry.COLUMN_GENRE_ID_LIST, genreIdList)

        return contentValues
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || !MovieModel::class.java.isAssignableFrom(other.javaClass)) {
            return false
        }

        return id == (other as MovieModel).id
    }

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + id.hashCode()
        return result
    }
}
