package com.example.madlevel6task2.api

import okhttp3.OkHttpClient
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

            // Create an OkHttpClient to be able to make a log of the network traffic
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(KeyInterceptor())
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

            // Create the Retrofit instance
            val triviaApi = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            // Return the Retrofit NumbersApiService
            return triviaApi.create(TheMovieDatabaseApiService::class.java)
        }
    }
}