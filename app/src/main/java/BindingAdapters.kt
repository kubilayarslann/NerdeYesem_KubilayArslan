package com.example.nerdeyesem

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.nerdeyesem.network.response.nearby_restaurants.NearbyRestaurant
import com.example.nerdeyesem.overview.RestaurantGridAdapter
import com.example.nerdeyesem.overview.ZomatoAPIStatus

@BindingAdapter("zomatoAPIStatus")
fun bindStatus(statusImageView: ImageView, status: ZomatoAPIStatus?) {
    when (status) {
        ZomatoAPIStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        ZomatoAPIStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.connection_error)
        }
        ZomatoAPIStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<NearbyRestaurant>?) {
    val adapter = recyclerView.adapter as RestaurantGridAdapter
    adapter.submitList(data)
}

@BindingAdapter("thumb")
fun bindImage(imgView: ImageView, thumb: String?) {
    thumb?.let {
        val imgUri = thumb.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(thumb)
            .apply(RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.broken_image))
            .into(imgView)
    }
}