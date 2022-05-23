package com.example.nerdeyesem.title

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.nerdeyesem.R
import com.example.nerdeyesem.com.example.nerdeyesem.login.LoginViewModel
import com.example.nerdeyesem.com.example.nerdeyesem.title.TitleViewModel
import com.example.nerdeyesem.databinding.FragmentTitleBinding
import com.example.nerdeyesem.detail.DetailFragmentArgs
import java.util.concurrent.Executor


class TitleFragment : Fragment() {

    private val viewModel: TitleViewModel by lazy { ViewModelProvider(this).get(TitleViewModel::class.java) }




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         //Inflate the layout for this fragment
        val binding : FragmentTitleBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_title, container, false)
        binding.lifecycleOwner = this
        binding.usernameText.text = viewModel.currentuser.value?.let {
            it.displayName
        }


        binding.searchButton.setOnClickListener{
            findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToOverviewFragment())
        }
        binding.signoutButton.setOnClickListener{
            viewModel.signOut()
            findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToLoginFragment())
        }
        return binding.root
    }
}