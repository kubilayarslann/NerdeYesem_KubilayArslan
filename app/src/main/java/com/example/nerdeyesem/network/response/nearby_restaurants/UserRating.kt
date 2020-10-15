package com.example.nerdeyesem.network.response.nearby_restaurants
import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserRating(
    val aggregate_rating : String,
    val rating_text : String,
    val rating_color : String,
    val votes : String
):Parcelable