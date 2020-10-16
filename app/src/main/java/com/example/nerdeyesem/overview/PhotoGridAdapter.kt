package com.example.nerdeyesem.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nerdeyesem.databinding.GridViewItemBinding
import com.example.nerdeyesem.network.response.nearby_restaurants.NearbyRestaurant
import com.example.nerdeyesem.network.response.nearby_restaurants.Restaurant

class PhotoGridAdapter(private val onClickListener: OnClickListener) : ListAdapter<NearbyRestaurant, PhotoGridAdapter.NearbyRestaurantViewHolder>(DiffCallback) {
    class NearbyRestaurantViewHolder (private var binding : GridViewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(nearbyRestaurant: NearbyRestaurant) {
                binding.viewModel = nearbyRestaurant
                binding.executePendingBindings()

        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<NearbyRestaurant>() {

        override fun areItemsTheSame(oldItem: NearbyRestaurant, newItem: NearbyRestaurant): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: NearbyRestaurant, newItem: NearbyRestaurant): Boolean {
            return oldItem.restaurant.id == newItem.restaurant.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoGridAdapter.NearbyRestaurantViewHolder {
        return NearbyRestaurantViewHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: PhotoGridAdapter.NearbyRestaurantViewHolder, position: Int) {
        val nearbyRestaurant = getItem(position)
        holder.itemView.setOnClickListener{
            onClickListener.onClick(nearbyRestaurant)
        }
        holder.bind(nearbyRestaurant)
    }

    class OnClickListener(val clickListener: (nearbyRestaurant: NearbyRestaurant)-> Unit){
        fun onClick (nearbyRestaurant: NearbyRestaurant) = clickListener(nearbyRestaurant)
    }

}