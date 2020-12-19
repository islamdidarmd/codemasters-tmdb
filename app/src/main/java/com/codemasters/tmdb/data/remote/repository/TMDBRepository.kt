package com.codemasters.tmdb.data.remote.repository

import com.codemasters.tmdb.data.model.Movie
import com.codemasters.tmdb.data.model.PaginatedResponse
import com.codemasters.tmdb.data.model.Trending
import com.codemasters.tmdb.data.model.TvSeries
import com.codemasters.tmdb.data.remote.ApiClient.createApiService
import com.codemasters.tmdb.data.remote.ApiService
import com.google.gson.Gson
import retrofit2.Response

class TMDBRepository {
    suspend fun getPopularMovies(): PaginatedResponse<Movie>? {
        return try {
            val api = createApiService<ApiService>()
            val response = api.getPopularMovies()
            handleApiResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    suspend fun getPopularTvSeries(): PaginatedResponse<TvSeries>? {
        return try {
            val api = createApiService<ApiService>()
            val response = api.getPopularTvSeries()
            handleApiResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    suspend fun getTrendingContent(): PaginatedResponse<Trending>? {
        return try {
            val api = createApiService<ApiService>()
            val response = api.getTrendingContent()
            handleApiResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    private inline fun <reified T> handleApiResponse(response: Response<T>): T? {
        return if (response.isSuccessful) response.body()
        else {
            val parsed: T? = Gson().fromJson(response.errorBody()?.string(), T::class.java)
            parsed
        }
    }
}