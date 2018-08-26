package com.themovielist.repository.favorite;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.themovielist.model.MovieModel;


public class MovieProvider extends ContentProvider {
    private static final int CODE_MOVIES = 101;

    private static final int CODE_MOVIE_DETAIL = 103;

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIE, CODE_MOVIES);
        URI_MATCHER.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIE + "/*", CODE_MOVIE_DETAIL);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] columns, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sort) {
        final int code = URI_MATCHER.match(uri);
        if (code == UriMatcher.NO_MATCH) {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        final Context context = getContext();
        if (context == null) {
            return null;
        }

        MovieDAO movieDAO = MovieDatabaseRoom.Companion.getInstance(context).movieDAO();
        final Cursor cursor;
        if (code == CODE_MOVIES) {
            cursor = movieDAO.selectAll();
        } else {
            cursor = movieDAO.selectById((int) ContentUris.parseId(uri));
        }

        cursor.setNotificationUri(context.getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new RuntimeException("Not implemented");
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        switch (URI_MATCHER.match(uri)) {
            case CODE_MOVIES:
                final Context context = getContext();
                if (context == null || contentValues == null) {
                    return null;
                }

                final long id = MovieDatabaseRoom.Companion.getInstance(context).movieDAO()
                        .insert(new MovieModel(contentValues));

                if (id <= 0) {
                    throw new SQLException("Failed to insert a movie into " + uri);
                }

                context.getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            case CODE_MOVIE_DETAIL:
                throw new IllegalArgumentException("You can only insert a movie using the /movie path.");
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        switch (URI_MATCHER.match(uri)) {
            case CODE_MOVIES:
                throw new IllegalArgumentException("You can only remove a movie using the /movie/:movieId path.");
            case CODE_MOVIE_DETAIL:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }

                final int count = MovieDatabaseRoom.Companion.getInstance(context).movieDAO()
                        .deleteById((int) ContentUris.parseId(uri));

                context.getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        throw new RuntimeException("Not implemented");
    }
}
