package com.themovielist.di.module

import android.content.Context
import androidx.room.Room
import com.google.gson.*
import com.themovielist.BuildConfig
import com.themovielist.model.ApiConfigurationModel
import com.themovielist.repository.favorite.TheMovieListDB
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Singleton

@Module
object AppModule {
    @Provides
    @JvmStatic
    @Reusable
    fun provideRetrofit(): Retrofit {
        val httpClient = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logging)
        }

        httpClient.addInterceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            val urlBuilder = chain.request().url.newBuilder()
            urlBuilder.addQueryParameter("api_key", BuildConfig.API_KEY)
            urlBuilder.addQueryParameter("region", Locale.getDefault().country)
            requestBuilder.url(urlBuilder.build())
            chain.proceed(requestBuilder.build())
        }

        return Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(buildGsonConverter())
                .client(httpClient.build())
                .build()
    }

    @Provides
    @JvmStatic
    @Reusable
    fun provideApiConfigurationFactory(): ApiConfigurationFactory {
        return ApiConfigurationFactory()
    }

    private fun buildGsonConverter(): Converter.Factory {
        val gson = GsonBuilder()
                // Handle empty release_date cases.
                .registerTypeAdapter(Date::class.java, object : JsonDeserializer<Date> {
                    var dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

                    @Throws(JsonParseException::class)
                    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Date? {
                        return try {
                            if (json.asString.isNullOrEmpty())
                                null
                            else
                                dateFormat.parse(json.asString)
                        } catch (e: ParseException) {
                            null
                        }

                    }
                })
                .create()

        val dateTypeAdapter = gson.getAdapter(Date::class.java)

        val safeDateTypeAdapter = dateTypeAdapter.nullSafe()

        val gsonBuilder = GsonBuilder()
                // .setDateFormat(DEFAULT_DATE_FORMAT)
                .registerTypeAdapter(Date::class.java, safeDateTypeAdapter)

        return GsonConverterFactory.create(gsonBuilder.create())
    }

    @Provides
    @JvmStatic
    @Singleton
    fun provideRoomDataBase(context: Context): TheMovieListDB {
        return Room.databaseBuilder(context, TheMovieListDB::class.java, "the-movie-list.db")
                .build()
    }
}

data class ApiConfigurationFactory constructor(val apiConfigurationModel: ApiConfigurationModel = ApiConfigurationModel())

