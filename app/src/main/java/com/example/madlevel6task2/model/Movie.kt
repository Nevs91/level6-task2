package com.example.madlevel6task2.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

class MovieJson {

    /**
     * Base class for the Json response send back from the API request
     */
    data class Base(
        @SerializedName("page") val page: Int,
        @SerializedName("total_results") val total_results: Int,
        @SerializedName("total_pages") val total_pages: Int,
        @SerializedName("results") val results: List<Movie>
    )

    /**
     * Parcelize Movie data class to convert the json fields into an object
     */
    @Parcelize
    data class Movie(
        @SerializedName("id") val id: Int,
        @SerializedName("title") val title: String,
        @SerializedName("overview") val overview: String,
        @SerializedName("popularity") val popularity: Double,
        @SerializedName("release_date") val release_date: String,
        @SerializedName("poster_path") val poster_path: String,
        @SerializedName("backdrop_path") val backdrop_path: String,
        @SerializedName("vote_count") val vote_count: Int,
        @SerializedName("video") val video: Boolean,
        @SerializedName("adult") val adult: Boolean,
        @SerializedName("original_language") val original_language: String,
        @SerializedName("genre_ids") val genre_ids: List<Int>,
        @SerializedName("vote_average") val vote_average: Double,
    ) : Parcelable {
        fun getMovieImgUrl(imgPath: String): String {
            return IMAGE_BASE_URL + imgPath
        }
    }

    companion object {
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/original"
    }
}