package com.codemasters.tmdb.data.model

import com.google.gson.annotations.SerializedName

data class Trending(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("release_date")
    val releaseDate: String?,

    @SerializedName("first_air_date")
    val firstAirDate: String,

    @SerializedName("vote_count")
    val voteCount: Int,

    @SerializedName("poster_path")
    val poster: String? = null,

    @SerializedName("media_type")
    val mediaType: String
)
