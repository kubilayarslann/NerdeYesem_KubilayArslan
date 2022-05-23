package com.example.nerdeyesem.com.example.nerdeyesem.login

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.hardware.biometrics.BiometricManager.Authenticators.BIOMETRIC_STRONG
import android.hardware.biometrics.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import android.os.Bundle
import android.text.Layout
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.nerdeyesem.R
import com.example.nerdeyesem.databinding.FragmentLoginfirebaseuiBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Executor
import kotlin.properties.Delegates

class LoginFragment: Fragment() {
    private  val RC_SIGN_IN = 123
    private val viewModel: LoginViewModel by lazy { ViewModelProvider(this).get(LoginViewModel::class.java) }
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private var backpressListener: Int = 0
    private var fingerChecker : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentLoginfirebaseuiBinding>(inflater, R.layout.fragment_loginfirebaseui, container, false)
        binding.lifecycleOwner = this

        viewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticationState ->
            when(authenticationState){
                LoginViewModel.AuthenticationState.AUTHENTICATED->{
                    val mContext = activity?.let {
                        it.applicationContext
                    }
                    if (fingerChecker){
                        executor = ContextCompat.getMainExecutor(mContext)
                        biometricPrompt = BiometricPrompt(this, executor,
                                object : BiometricPrompt.AuthenticationCallback() {
                                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {//if there isnt  any saved fingerprint , thats going an eror and launch the mail login.
                                        super.onAuthenticationError(errorCode, errString)
                                        Toast.makeText(mContext,
                                                "Authentication error: $errString", Toast.LENGTH_LONG)
                                                .show()
                                        FirebaseAuth.getInstance().signOut()
                                    }

                                    override fun onAuthenticationSucceeded(
                                            result: BiometricPrompt.AuthenticationResult) {
                                        super.onAuthenticationSucceeded(result)
                                        Toast.makeText(mContext,
                                                "Authentication succeeded!", Toast.LENGTH_LONG)
                                                .show()
                                        this@LoginFragment.findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToTitleFragment())
                                    }

                                    override fun onAuthenticationFailed() {
                                        super.onAuthenticationFailed()
                                        Toast.makeText(mContext,
                                                "Authentication failed", Toast.LENGTH_LONG)
                                                .show()
                                    }
                                })

                        promptInfo = BiometricPrompt.PromptInfo.Builder()
                                .setTitle("Biometric login for my app")
                                .setSubtitle("Log in using your biometric credential")
                                .setNegativeButtonText("Use account password")
                                .build()

                        biometricPrompt.authenticate(promptInfo)
                    }
                }

                else -> {
                    if (backpressListener < 1) {    //Checking the situation after the first launch
                        launchSignInFlow()
                        backpressListener++
                    }
                    else{
                        activity?.let {
                            it.finish()
                       }
                    }
                }
            }
        })


        return binding.root
    }

    fun launchSignInFlow(){
        fingerChecker = false
        val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build())

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setIsSmartLockEnabled(false)
                        .build(),
                RC_SIGN_IN
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                Log.i(TAG, "Successfully signed in user ${FirebaseAuth.getInstance().currentUser?.displayName}!")
                this.findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToTitleFragment())

            } else {
                Log.i(TAG, "Sign in unsuccessful ${response?.error?.errorCode}")
            }
        }
        else{
            activity?.let {
              it.finish()
            }
        }

    }

}