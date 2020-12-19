package com.codemasters.tmdb.data.model

import com.google.gson.annotations.SerializedName

data class PaginatedResponse<T>(
    @SerializedName("success")
    val success: Boolean? = null,

    @SerializedName("status_message")
    val message: String?,

    @SerializedName("status_code")
    val statusCode: Int = -1,

    @SerializedName("page")
    val page: Int,

    @SerializedName("results")
    val results: List<T>,
)
