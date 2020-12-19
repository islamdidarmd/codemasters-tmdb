package com.codemasters.tmdb.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "wishlist")
data class ContentDetails(
    @PrimaryKey(autoGenerate = true) val localId: Int = 0,

    @SerializedName("id")
    val id: Int,

    @SerializedName("content_type")
    val content_type: String,

    @SerializedName("success")
    val success: Boolean? = null,

    @SerializedName("status_message")
    val message: String?,

    @SerializedName("status_code")
    val statusCode: Int? = -1,

    @SerializedName("first_air_date")
    val releaseDate: String?,

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
