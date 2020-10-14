package com.example.nerdeyesem.network.response.nearby_restaurants
import com.squareup.moshi.Json

data class Locality(
    val entity_type : String,
    val entity_id : Int,
    val title : String,
    val latitude : Double,
    val longitude : Double,
    val city_id : Int,
    val city_name : String,
    val country_id : Int,
    val country_name : String
)