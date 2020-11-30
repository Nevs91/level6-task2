package com.example.madlevel6task2.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.madlevel6task2.api.TheMovieDatabaseApi
import com.example.madlevel6task2.api.TheMovieDatabaseApiService
import com.example.madlevel6task2.model.Movie
import kotlinx.coroutines.withTimeout


class TheMovieDatabaseRepository {
    private val theMoviesDatabaseApiService: TheMovieDatabaseApiService = TheMovieDatabaseApi.createApi()

    private val _movies: MutableLiveData<List<Movie>> = MutableLiveData()

    /**
     * Expose non MutableLiveData via getter Encapsulation
     */
    val movies: LiveData<List<Movie>>
        get() = _movies

    /**
     * suspend function that calls a suspend function from the numbersApi call
     */
    suspend fun getPopularMoviesByYear(year: Number)  {
        try {
            //timeout the request after 5 seconds
            val result = withTimeout(5_000) {
                theMoviesDatabaseApiService.getPopularMovies(year, "popularity.desc")
            }

            _movies.value = result
        } catch (error: Throwable) {
            throw MoviesDbApiError("Unable to retrieve movies", error)
        }
    }

    class MoviesDbApiError(message: String, cause: Throwable) : Throwable(message, cause)
}