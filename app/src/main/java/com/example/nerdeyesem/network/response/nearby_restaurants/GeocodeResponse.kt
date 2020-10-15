package com.example.nerdeyesem.network.response.nearby_restaurants

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GeocodeResponse(
    @Json(name = "location") val locality :  Locality,
    val popularity : Popularity,
    val link : String,
    val nearby_restaurants : List<NearbyRestaurant>
): Parcelable