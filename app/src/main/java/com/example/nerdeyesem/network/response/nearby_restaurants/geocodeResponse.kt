package com.example.nerdeyesem.network.response.nearby_restaurants

import com.squareup.moshi.Json

data class geocodeResponse(
    @Json(name = "location") val locality :  Locality,
    val popularity : Popularity,
    val link : String,
    val nearby_restaurants : List<nearbyRestaurant>
)