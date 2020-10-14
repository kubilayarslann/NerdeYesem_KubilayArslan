package com.example.nerdeyesem.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nerdeyesem.network.ZomatoProperty

class DetailViewModelFactory(
    private val zomatoProperty: ZomatoProperty,
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(zomatoProperty, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}