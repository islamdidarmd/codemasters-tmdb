package com.codemasters.tmdb.ui.content_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.codemasters.tmdb.data.model.*
import com.codemasters.tmdb.data.remote.repository.ContentRepository
import kotlinx.coroutines.Dispatchers

class ContentDetailsViewModel : ViewModel() {
    private val repository by lazy {
        ContentRepository()
    }

    fun getContentDetails(
        contentType: ContentType,
        id: Int
    ): LiveData<StatefulData<ContentDetails>> {
        return liveData(Dispatchers.Default) {
            emit(StatefulData.loading())

            val response = repository.getContentDetails(contentType, id)

            if (response?.success != false) {
                emit(StatefulData.success(response))
            } else emit(StatefulData.error(response))
        }
    }
}