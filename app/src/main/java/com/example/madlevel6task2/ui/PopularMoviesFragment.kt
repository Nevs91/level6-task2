package com.example.madlevel6task2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.madlevel6task2.R
import com.example.madlevel6task2.adapter.MovieAdapter
import com.example.madlevel6task2.model.MovieJson.Movie
import com.example.madlevel6task2.viewModel.TheMovieDatabaseViewModel
import kotlinx.android.synthetic.main.fragment_popular_movies.*

const val REQ_MOVIE_KEY = "req_movie"
const val BUNDLE_MOVIE_KEY = "bundle_movie"

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PopularMoviesFragment : Fragment() {

    private val viewModel: TheMovieDatabaseViewModel by viewModels()

    private val movies = arrayListOf<Movie>()
    private val moviesAdapter = MovieAdapter(movies, ::onMovieClick)

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_popular_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvMovies.apply {
            adapter = moviesAdapter
            layoutManager = GridLayoutManager(context, 2)
        }

        btnSubmit.setOnClickListener {
            this.validateAndSubmitMovieYear()
        }

        // Observe the LiveData in the viewModel for any error messages
        this.observeMovies()
    }

    /**
     * Send data to the movie_details fragment with the clicked movie
     */
    private fun onMovieClick(movie: Movie) {
        setFragmentResult(REQ_MOVIE_KEY, bundleOf(Pair(BUNDLE_MOVIE_KEY, movie)))
        findNavController().navigate(R.id.action_PopularMoviesFragment_to_MovieDetailFragment)
    }

    /**
     * Validate and submit the year input field.
     * When valid request a list of movies, otherwise return with an error message
     */
    private fun validateAndSubmitMovieYear() {

        // Check if the year field has 4 digits
        if (etYear == null || etYear.length() != 4) {
            Toast.makeText(activity, "Invalid year (must be 4 numbers)", Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.getPopularMovies(Integer.parseInt(etYear.text.toString()))
    }

    /**
     * Method to observe and show errors when making API requests
     */
    private fun observeMovies() {
        viewModel.errorText.observe(viewLifecycleOwner, { message ->
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
        })

        viewModel.movies.observe(viewLifecycleOwner, {
            movies.clear()
            movies.addAll(it)
            moviesAdapter.notifyDataSetChanged()
        })
    }
}