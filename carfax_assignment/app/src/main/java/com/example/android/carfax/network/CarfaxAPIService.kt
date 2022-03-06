package com.example.android.carfax.network

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://carfax-for-consumers.firebaseio.com/"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory((RxJava2CallAdapterFactory.create()))
                       .baseUrl(BASE_URL).build()

interface CarfaxAPIService {
    @GET("assignment.json")
    fun getData(): Observable<CarfaxData>
}

object CarfaxAPI {
    val retrofitService : CarfaxAPIService by lazy {
        retrofit.create(CarfaxAPIService::class.java)
    }
}

