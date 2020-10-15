package com.example.nerdeyesem.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.nerdeyesem.network.ZomatoProperty
import com.example.nerdeyesem.network.response.nearby_restaurants.NearbyRestaurant

class DetailViewModel(nearbyRestaurant: NearbyRestaurant, app: Application) : AndroidViewModel(app) {

    private val _selectedRestaurant = MutableLiveData<NearbyRestaurant>()
    val selectedRestaurant: LiveData<NearbyRestaurant>
        get() = _selectedRestaurant

    init {
        _selectedRestaurant.value = nearbyRestaurant
    }
}