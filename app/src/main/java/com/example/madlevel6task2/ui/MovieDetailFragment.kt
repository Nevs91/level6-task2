package com.example.madlevel6task2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.bumptech.glide.Glide
import com.example.madlevel6task2.R
import com.example.madlevel6task2.model.MovieJson.Movie
import kotlinx.android.synthetic.main.fragment_movie_detail.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MovieDetailFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observePopularMoviesFragmentResult()
    }

    /**
     * Observe for any results send from the PopularMoviesFragment.
     * Load the contents of the results (if any) into the fragments layout
     */
    private fun observePopularMoviesFragmentResult() {
        setFragmentResultListener(REQ_MOVIE_KEY) { _, bundle ->
            bundle.getParcelable<Movie>(BUNDLE_MOVIE_KEY)?.let {
                setFragmentContent(it)
            }
        }
    }

    /**
     * Load the different attributes of the movie instance into the fragments layout
     */
    private fun setFragmentContent(movie: Movie) {
        tvTitle.text = movie.title

        tvRelease.text = movie.release_date
        tvRating.text = movie.vote_average.toString()
        tvDescription.text = movie.overview

        // Set the movie banner and cover images
        context?.let {
            Glide.with(it).load(movie.getMovieImgUrl(movie.backdrop_path)).into(ivBanner)
        }
        context?.let {
            Glide.with(it).load(movie.getMovieImgUrl(movie.poster_path)).into(ivCover)
        }
    }
}