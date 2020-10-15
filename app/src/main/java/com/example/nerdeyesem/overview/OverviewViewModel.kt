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

class OverviewViewModel : ViewModel() {

    // The internal MutableLiveData String that stores the status of the most recent request
    private val _status = MutableLiveData<String>()

    // The external immutable LiveData for the request status String
    val status: LiveData<String>
        get() = _status

    //Create a coroutine Job and a CoroutineScope using MainDispatcher
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    private val _nearbyRestaurants = MutableLiveData<NearbyRestaurant>()
    val nearbyRestaurant : LiveData<NearbyRestaurant>
        get() = _nearbyRestaurants

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
                var response = getNearbyRestaurantsDeferred.await()
                _nearbyRestaurants.value =  response.nearby_restaurants[0]
            }catch (t: Throwable){
                _status.value = "Failure: " + t.message
            }

        }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}