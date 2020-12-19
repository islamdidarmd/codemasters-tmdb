package com.codemasters.tmdb.data.model

import com.google.gson.annotations.SerializedName

data class ContentDetails(
    @SerializedName("success")
    val success: Boolean? = null,

    @SerializedName("status_message")
    val message: String?,

    @SerializedName("status_code")
    val statusCode: Int = -1,

    @SerializedName("first_air_date")
    val releaseDate: String,

    @SerializedName("backdrop_path")
    val backDrop: String? = null,

    @SerializedName("poster_path")
    val poster: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("vote_average")
    val vote_average: Double,

    @SerializedName("overview")
    val overview: String? = null
)
