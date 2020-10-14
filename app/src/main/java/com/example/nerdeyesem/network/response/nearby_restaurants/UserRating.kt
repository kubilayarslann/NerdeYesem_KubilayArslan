package com.example.nerdeyesem.network.response.nearby_restaurants
import com.squareup.moshi.Json
data class UserRating(
    val aggregate_rating : String,

    val rating_text : String,

    val rating_color : String,

    val votes : String
)