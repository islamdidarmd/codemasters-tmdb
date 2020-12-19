package com.codemasters.tmdb.data.model

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("title")
    val title: String,

    @SerializedName("release_date")
    val releaseDate: String,

    @SerializedName("vote_count")
    val voteCount: Int,

    @SerializedName("poster_path")
    val poster: String? = null
)