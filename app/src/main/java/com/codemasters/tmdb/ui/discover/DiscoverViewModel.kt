package com.codemasters.tmdb.ui.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.codemasters.tmdb.data.model.*
import com.codemasters.tmdb.data.remote.repository.TMDBRepository
import kotlinx.coroutines.Dispatchers

class DiscoverViewModel : ViewModel() {
    private val repository by lazy {
        TMDBRepository()
    }

    fun getPopularMovies(): LiveData<StatefulData<PaginatedResponse<Movie>>?> {
        return liveData(Dispatchers.Default) {
            emit(StatefulData.loading())

            val response = repository.getPopularMovies()

            if (response?.success != false) {
                emit(StatefulData.success(response))
            } else emit(StatefulData.error(response))
        }
    }

    fun getPopularTvSeries(): LiveData<StatefulData<PaginatedResponse<TvSeries>>?> {
        return liveData(Dispatchers.Default) {
            emit(StatefulData.loading())

            val response = repository.getPopularTvSeries()

            if (response?.success != false) {
                emit(StatefulData.success(response))
            } else emit(StatefulData.error(response))
        }
    }

    fun getTrendingContent(): LiveData<StatefulData<PaginatedResponse<Trending>>?> {
        return liveData(Dispatchers.Default) {
            emit(StatefulData.loading())

            val response = repository.getTrendingContent()

            if (response?.success != false) {
                emit(StatefulData.success(response))
            } else emit(StatefulData.error(response))
        }
    }
}