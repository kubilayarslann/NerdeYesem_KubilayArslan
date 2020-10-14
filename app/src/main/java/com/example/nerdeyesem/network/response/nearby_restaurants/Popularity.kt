package com.example.nerdeyesem.network.response.nearby_restaurants
import com.squareup.moshi.Json
data class Popularity(

    val popularity : String,

    val nightlife_index : String,

    val top_cuisines : List<String>

)