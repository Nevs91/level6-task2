package com.example.madlevel6task2.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.madlevel6task2.repository.TheMovieDatabaseRepository
import kotlinx.coroutines.launch

class TheMovieDatabaseViewModel(application: Application) : AndroidViewModel(application) {
    private val moviesRepository = TheMovieDatabaseRepository()

    /**
     * This property points direct to the LiveData in the repository, that value
     * get's updated when user submits a new year to retrieve popular movies for.
     */
    val movies = moviesRepository.movies

    private val _errorText: MutableLiveData<String> = MutableLiveData()

    /**
     * Expose non MutableLiveData via getter
     * errorText can be observed from Activity for error showing
     * Encapsulation :)
     */
    val errorText: LiveData<String>
        get() = _errorText

    /**
     * The viewModelScope is bound to Dispatchers.Main and will automatically be cancelled when the ViewModel is cleared.
     * Extension method of lifecycle-viewmodel-ktx library
     */
    fun getPopularMovies(year: Number) {
        viewModelScope.launch {
            try {
                moviesRepository.getPopularMoviesByYear(year)
            } catch (error: TheMovieDatabaseRepository.MoviesDbApiError) {
                _errorText.value = error.message
                Log.e("Movies DB API error", error.cause.toString())
            }
        }
    }
}