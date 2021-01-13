package ru.s1mple.myapp.network

import MovieDetails
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import ru.s1mple.myapp.data.ActorsData
import ru.s1mple.myapp.data.Genres
import ru.s1mple.myapp.data.Page


interface MoviesApi {
    @GET("movie/popular")
    suspend fun loadMovies(): Page

    @GET("movie/{movie_id}")
    suspend fun loadMovie(@Path("movie_id")mID : Long): MovieDetails

    @GET("genre/movie/list")
    suspend fun loadGenres(): Genres

    @GET("movie/{movie_id}/credits")
    suspend fun loadActors(@Path("movie_id")mID : Long): ActorsData
}

private class MoviesApiInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url

        val request = originalRequest.newBuilder()
            .url(originalHttpUrl)
            .addHeader("Authorization", API_KEY)
            .build()

        return chain.proceed(request)
    }
}

object RetrofitModule {
    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val client = OkHttpClient().newBuilder()
        .addInterceptor(MoviesApiInterceptor())
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    val moviesApi : MoviesApi = retrofit.create()
}

private const val API_KEY = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkZTIwZGM3NDhiNjcxMDcxYjVmNmVhOWYwODQ2OTc3OCIsInN1YiI6IjVmZjJmNzZjZmFiM2ZhMDAzYjc2MzY5NyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.nOz6CJYsZkuFq0Y7nO8wSCMfc6sSNk1WVUqmcw4Mxx8"