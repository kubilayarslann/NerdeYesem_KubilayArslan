package com.example.nerdeyesem.network.response.nearby_restaurants
import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Popularity(
    val popularity : String,
    val nightlife_index : String,
    val top_cuisines : List<String>

):Parcelable