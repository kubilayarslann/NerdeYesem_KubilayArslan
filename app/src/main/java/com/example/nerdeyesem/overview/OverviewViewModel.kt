package com.example.nerdeyesem.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nerdeyesem.network.API.ZomatoApi
import com.example.nerdeyesem.network.response.nearby_restaurants.geocodeResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OverviewViewModel : ViewModel() {

    // The internal MutableLiveData String that stores the status of the most recent request
    private val _response = MutableLiveData<String>()

    // The external immutable LiveData for the request status String
    val response: LiveData<String>
        get() = _response

    //Create a coroutine Job and a CoroutineScope using MainDispatcher
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

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
                _response.value =  response.toString()
            }catch (t: Throwable){
                _response.value = "Failure: " + t.message
            }

        }

//        ZomatoApi.retrofitService.getNearbyRestaurants("41.008921","28.973153").enqueue( object: Callback<geocodeResponse> {
//            override fun onFailure(call: Call<geocodeResponse>, t: Throwable) {
//                _response.value = "Failure: " + t.message
//            }
//
//            override fun onResponse(call: Call<geocodeResponse>, response: Response<geocodeResponse>) {
//                _response.value = "Success: ${response.body()} Restaurant properties retrieved"
//            }
//        })
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}