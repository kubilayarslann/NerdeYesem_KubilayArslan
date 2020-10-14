package com.example.nerdeyesem.network.API

import com.example.nerdeyesem.network.response.nearby_restaurants.geocodeResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


private const val BASE_URL = "https://developers.zomato.com/api/v2.1/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface ZomatoAPIService {
//    @Headers(
//        "Accept: application/json",
//        "user-key: 5304865f97a15a81bf07630ec09f4d63")
//    @GET("api/v2.1/search")
//    fun getProperties():
//            Call<String>
    @Headers("Accept: application/json", "user_key: 5304865f97a15a81bf07630ec09f4d63")
    @GET("geocode")
    fun getNearbyRestaurants(@Query("lat")queryParams1: String,
                             @Query("lon")queryParams2: String):Deferred<geocodeResponse>
//
//    @Headers("Accept: application/json", "user_key: 327e75c31ca03dbb55cbabe4257acfa9")
//    @GET("restaurant")
//    fun getRestaurantDetail(@Query("res_id") queryParameter: String):Call<RestaurantDetail>
//
}

object ZomatoApi {
    val retrofitService : ZomatoAPIService by lazy {retrofit.create(ZomatoAPIService::class.java)
    }
}