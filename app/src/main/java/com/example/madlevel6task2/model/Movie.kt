package com.example.madlevel6task2.model

import com.google.gson.annotations.SerializedName

data class Movie (
    @SerializedName("title") var title: String,
    @SerializedName("overview") var overview : String,
    @SerializedName("release_date") var releaseDate: String,
    @SerializedName("vote_average") var rating: Number,
    @SerializedName("backdrop_path") var backdropImgUrlPath: String,
    @SerializedName("poster_path") var posterImgUrlPath: String,
)