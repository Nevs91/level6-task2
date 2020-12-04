package com.example.madlevel6task2.api

import com.example.madlevel6task2.BuildConfig
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TheMovieDatabaseApi {
    companion object {
        // The base url off the api.
        private const val baseUrl = "https://api.themoviedb.org/3/"

        /**
         * @return [TheMovieDatabaseApiService] The service class off the retrofit client.
         */
        fun createApi(): TheMovieDatabaseApiService {

            // Create an OkHttpClient to intercept requests
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(KeyInterceptor())
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

            // Create the Retrofit instance
            val movieDatabaseApi= Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            // Return the Retrofit NumbersApiService
            return movieDatabaseApi.create(TheMovieDatabaseApiService::class.java)
        }
    }
}

class KeyInterceptor : Interceptor {

    /**
     * Intercept requests and add the API key as a query parameter
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val originalHttpUrl: HttpUrl = original.url

        // Add the API key to the request
        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("api_key", BuildConfig.MOVIE_DATABASE_KEY)
            .build()

        // Request customization: add request headers
        val requestBuilder: Request.Builder = original.newBuilder()
            .url(url)

        val request: Request = requestBuilder.build()
        return chain.proceed(request)
    }
}
