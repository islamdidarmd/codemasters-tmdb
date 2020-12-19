package com.codemasters.tmdb.data.model

import com.google.gson.annotations.SerializedName

data class Trending(
    @SerializedName("title")
    val title: String,

    @SerializedName("release_date")
    val releaseDate: String,

    @SerializedName("vote_average")
    val voteAverage: Double,

    @SerializedName("poster_path")
    val poster: String? = null
)
