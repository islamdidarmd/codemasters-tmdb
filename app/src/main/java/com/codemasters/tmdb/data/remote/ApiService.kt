package com.codemasters.tmdb.data.remote

import com.codemasters.tmdb.data.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("discover/movie")
    suspend fun getPopularMovies(
        @Query("primary_release_year") primary_release_year: String = "2020",
        @Query("sort_by") sortBy: String = "vote_average.desc"
    ): Response<PaginatedResponse<Movie>?>

    @GET("discover/tv")
    suspend fun getPopularTvSeries(
        @Query("primary_release_year") primary_release_year: String = "2020",
        @Query("sort_by") sortBy: String = "vote_average.desc"
    ): Response<PaginatedResponse<TvSeries>?>

    @GET("trending/all/week")
    suspend fun getTrendingContent(): Response<PaginatedResponse<Trending>?>

    @GET("/3/{content_type}/{id}")
    suspend fun getContentDetails(
        @Path("content_type") contentType: String,
        @Path("id") id: Int
    ): Response<ContentDetails?>
}