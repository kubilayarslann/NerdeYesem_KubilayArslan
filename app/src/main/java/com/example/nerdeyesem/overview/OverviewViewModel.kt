package com.example.nerdeyesem.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nerdeyesem.network.API.ZomatoApi
import com.example.nerdeyesem.network.response.nearby_restaurants.NearbyRestaurant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class ZomatoAPIStatus { LOADING, ERROR, DONE}

class OverviewViewModel : ViewModel() {

    // The internal MutableLiveData String that stores the status of the most recent request
    private val _status = MutableLiveData<ZomatoAPIStatus>()

    // The external immutable LiveData for the request status String
    val status: LiveData<ZomatoAPIStatus>
        get() = _status

    //Create a coroutine Job and a CoroutineScope using MainDispatcher
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    private val _nearbyRestaurants = MutableLiveData<List<NearbyRestaurant>>()
    val nearbyRestaurant : LiveData<List<NearbyRestaurant>>
        get() = _nearbyRestaurants

    private val _navigateToSelectednearbyRestaurant = MutableLiveData<NearbyRestaurant>()
    val navigateToSelectednearbyRestaurant : LiveData<NearbyRestaurant>
        get() = _navigateToSelectednearbyRestaurant

    /**
     * Call getZomatoProperties() on init so we can display status immediately.
     */
    init {
        getZomatoProperties()
    }

    /**
     * Sets the value of the status LiveData to the Zomato API status.
     */
    private fun getZomatoProperties() {

        coroutineScope.launch {

            var getNearbyRestaurantsDeferred = ZomatoApi.retrofitService.getNearbyRestaurants("41.008921","28.973153")
            try {
                _status.value = ZomatoAPIStatus.LOADING
                var response = getNearbyRestaurantsDeferred.await()
                _status.value = ZomatoAPIStatus.DONE
                _nearbyRestaurants.value =  response.nearby_restaurants
            }catch (t: Throwable){
                _status.value = ZomatoAPIStatus.ERROR
                _nearbyRestaurants.value = ArrayList()
            }

        }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun displayRestaurantDetails (nearbyRestaurant: NearbyRestaurant){
        _navigateToSelectednearbyRestaurant.value = nearbyRestaurant
    }
    fun displayRestaurantDetailsComplete(){
        _navigateToSelectednearbyRestaurant.value = null
    }
}