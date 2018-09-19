package com.themovielist.di

import android.content.Context
import com.google.gson.*
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import com.themovielist.BuildConfig
import com.themovielist.MainApplication
import com.themovielist.domain.FavoriteMovieUseCase
import com.themovielist.model.ApiConfigurationModel
import com.themovielist.repository.favorite.FavoriteRepository
import dagger.Module
import dagger.Provides
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
class AppModule {

    @Provides
    fun provideContext(application: MainApplication): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideFavoriteMovieUseCase(favoriteRepository: FavoriteRepository): FavoriteMovieUseCase {
        return FavoriteMovieUseCase(favoriteRepository)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        // Add a log interceptor
        val httpClient = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logging)
        }

        httpClient.addInterceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            val urlBuilder = chain.request().url().newBuilder()
            urlBuilder.addQueryParameter("api_key", BuildConfig.API_KEY)
            urlBuilder.addQueryParameter("region", Locale.getDefault().country)
            requestBuilder.url(urlBuilder.build())
            chain.proceed(requestBuilder.build())
        }

        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(buildGsonConverter())
                //.addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(httpClient.build())
                .build()
    }

    @Provides
    @Singleton
    fun provideApiConfigurationFactory(): ApiConfigurationFactory {
        return ApiConfigurationFactory()
    }

    private fun buildGsonConverter(): Converter.Factory {
        val gson = GsonBuilder()
                // Handle empty release_date cases.
                .registerTypeAdapter(Date::class.java, object : JsonDeserializer<Date> {
                    var dateFormat: DateFormat = SimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.getDefault())
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
                .setDateFormat(DEFAULT_DATE_FORMAT)
                .registerTypeAdapter(Date::class.java, safeDateTypeAdapter)

        return GsonConverterFactory.create(gsonBuilder.create())
    }

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"
        private const val DEFAULT_DATE_FORMAT = "yyyy-MM-dd"
    }
}

data class ApiConfigurationFactory constructor(val apiConfigurationModel: ApiConfigurationModel = ApiConfigurationModel())

