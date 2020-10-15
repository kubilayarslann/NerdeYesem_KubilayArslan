package com.example.nerdeyesem.network.response.nearby_restaurants
import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NearbyRestaurant (
    val restaurant : Restaurant
):Parcelable