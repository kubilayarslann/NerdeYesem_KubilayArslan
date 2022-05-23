package com.example.nerdeyesem.com.example.nerdeyesem.title

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class TitleViewModel : ViewModel(){
    private val firebaseAuth = FirebaseAuth.getInstance()

    private val _currentuser = MutableLiveData<FirebaseUser>()
    val currentuser: LiveData<FirebaseUser>
        get() = _currentuser

    init {
        _currentuser.value = firebaseAuth.currentUser
    }

    fun signOut(){
        firebaseAuth.signOut()
    }

}