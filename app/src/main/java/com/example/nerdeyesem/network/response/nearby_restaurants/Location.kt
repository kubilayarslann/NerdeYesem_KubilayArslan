package com.example.nerdeyesem.network.response.nearby_restaurants
import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Location (
    val address : String,
    val locality : String,
    val city : String,
    val city_id : Int,
    val latitude : Double,
    val longitude : Double,
    val zipcode : String,
    val country_id : Int,
    val locality_verbose : String
):Parcelable