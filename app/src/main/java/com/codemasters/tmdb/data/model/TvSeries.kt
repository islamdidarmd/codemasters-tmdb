package com.codemasters.tmdb.data.model

import com.google.gson.annotations.SerializedName

data class TvSeries(
    @SerializedName("name")
    val title: String,

    @SerializedName("first_air_date")
    val releaseDate: String,

    @SerializedName("vote_count")
    val voteCount: Int,

    @SerializedName("poster_path")
    val poster: String? = null
)