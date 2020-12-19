package com.codemasters.tmdb.data.remote.repository

import com.google.gson.Gson
import retrofit2.Response

open class BaseRepository {
    protected inline fun <reified T> handleApiResponse(response: Response<T>): T? {
        return if (response.isSuccessful) response.body()
        else {
            val parsed: T? = Gson().fromJson(response.errorBody()?.string(), T::class.java)
            parsed
        }
    }
}