package com.example.nerdeyesem.network.response.nearby_restaurants
import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Restaurant(
    val  R : R,
    val apikey : String,
    val id : String,
    val name : String,
    val url : String,
    val location : Location,
    val switch_to_order_menu : Int,
    val cuisines: String,
    val average_cost_for_two: Int,
    val price_range : Int,
    val currency : String,
    val opentable_support : Int,
    val is_zomato_book_res :  Int,
    val mezzo_provider : String,
    val is_book_form_web_view : Int,
    val book_form_web_view_url : String,
    val thumb : String,
    val user_rating : UserRating,
    val photos_url : String,
    val menu_url : String,
    val featured_image : String,
    val has_online_delivery : Int,
    val is_delivering_now : Int,
    val include_bogo_offers : Boolean,
    val deeplink : String,
    val is_table_reservation_supported : Int,
    val has_table_booking : Int,
    val events_url : String
):Parcelable