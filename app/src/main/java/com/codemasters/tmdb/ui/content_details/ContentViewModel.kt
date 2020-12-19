package com.codemasters.tmdb.ui.content_details

import androidx.lifecycle.*
import com.codemasters.tmdb.data.local.ContentDatabase
import com.codemasters.tmdb.data.model.*
import com.codemasters.tmdb.data.remote.repository.ContentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ContentViewModel(application: android.app.Application) : AndroidViewModel(application) {
    private val repository by lazy {
        ContentRepository()
    }

    private val db by lazy {
        ContentDatabase.getSpeedDialDatabase(application)
    }

    fun getContentDetails(
        mediaType: String,
        id: Int
    ): LiveData<StatefulData<ContentDetails>> {
        return liveData(Dispatchers.Default) {
            emit(StatefulData.loading())

            val response = repository.getContentDetails(mediaType, id)

            if (response?.success != false) {
                emit(StatefulData.success(response))
            } else emit(StatefulData.error(response))
        }
    }

    fun getWishList(): Flow<List<ContentDetails>> {
        return db.getDao().getAll()
    }

    fun deleteContent(content: ContentDetails) {
        viewModelScope.launch(Dispatchers.Default) {
            db.getDao().delete(content.id)
        }
    }

    fun insertContent(content: ContentDetails) {
        viewModelScope.launch(Dispatchers.Default) {
            db.getDao().insert(content)
        }
    }
}