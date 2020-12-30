package com.example.nerdeyesem.com.example.nerdeyesem.network.response.nearby_restaurants

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RateBackgroundColor(
        val type : String,
        val tint : String
):Parcelable