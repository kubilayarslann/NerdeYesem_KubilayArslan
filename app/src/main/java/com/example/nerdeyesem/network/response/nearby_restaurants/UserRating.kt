package com.example.nerdeyesem.network.response.nearby_restaurants
import android.os.Parcelable
import com.example.nerdeyesem.com.example.nerdeyesem.network.response.nearby_restaurants.RatingObject
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserRating(
        val aggregate_rating : String,
        val rating_text : String,
        val rating_color : String,
        val rating_obj : RatingObject,
        val votes : String

):Parcelable