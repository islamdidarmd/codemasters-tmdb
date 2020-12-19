package com.codemasters.tmdb.data.remote

import com.codemasters.tmdb.BuildConfig
import com.codemasters.tmdb.utils.BASE_URL
import com.codemasters.tmdb.utils.OKHTTP_CONNECT_TIMEOUT
import com.codemasters.tmdb.utils.OKHTTP_READ_TIMEOUT
import com.codemasters.tmdb.utils.OKHTTP_WRITE_TIMEOUT
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    //singleton Retrofit Instance
    val mInstance: Retrofit by lazy {
        createInstance(BASE_URL)
    }

   private fun createInstance(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .client(getOkHttpClient())
            .build()
    }

    inline fun <reified T> createApiService(): T {
        return mInstance.create(T::class.java)
    }

    private fun getOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .addInterceptor(getLoggingInterceptor())
            .connectTimeout(OKHTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(OKHTTP_READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(OKHTTP_WRITE_TIMEOUT, TimeUnit.SECONDS)
        return builder.build()
    }

    private fun getLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
        return logging
    }
}