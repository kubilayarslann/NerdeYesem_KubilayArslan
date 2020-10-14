package com.example.nerdeyesem.network.response.nearby_restaurants
import com.squareup.moshi.Json

data class Location (

    val address : String,
    @Json(name = "locality")val localityName : String,
    val city : String,
    val city_id : Int,
    val latitude : Double,
    val longitude : Double,
    val zipcode : String,
    val country_id : Int,
    val locality_verbose : String
)