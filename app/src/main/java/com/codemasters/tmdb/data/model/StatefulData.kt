package com.codemasters.tmdb.data.model

class StatefulData<T> private constructor(val status: Status, val data: T?) {
    enum class Status {
        SUCCESS, ERROR, LOADING
    }

    companion object {
        fun <T> success(data: T? = null): StatefulData<T> {
            return StatefulData(
                Status.SUCCESS,
                data
            )
        }

        fun <T> error(errorData: T? = null): StatefulData<T> {
            return StatefulData(
                Status.ERROR,
                errorData
            )
        }

        fun <T> loading(data: T? = null): StatefulData<T> {
            return StatefulData(
                Status.LOADING,
                data
            )
        }
    }
}