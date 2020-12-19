package com.codemasters.tmdb.data.remote.repository

import com.codemasters.tmdb.data.model.*
import com.codemasters.tmdb.data.remote.ApiClient.createApiService
import com.codemasters.tmdb.data.remote.ApiService
import com.google.gson.Gson
import retrofit2.Response

class ContentRepository : BaseRepository() {
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

    suspend fun getContentDetails(contentType: ContentType, id: Int): ContentDetails? {
        return try {
            val api = createApiService<ApiService>()
            val response = api.getContentDetails(contentType.name, id)
            handleApiResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}