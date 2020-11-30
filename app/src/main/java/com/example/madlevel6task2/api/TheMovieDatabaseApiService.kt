package com.example.madlevel6task2.api

import com.example.madlevel6task2.model.Movie
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieDatabaseApiService {

    // The GET method needed to retrieve popular movies for a specified year
    @GET("discover/movie?")
    suspend fun getPopularMovies(
        @Query("year") year: Number
    ): List<Movie>
}
