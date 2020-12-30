package com.example.nerdeyesem.overview

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.nerdeyesem.R
import com.example.nerdeyesem.databinding.FragmentOverviewBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class OverviewFragment : Fragment() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val viewModel: OverviewViewModel by lazy { ViewModelProvider(this).get(OverviewViewModel::class.java) }

    @SuppressLint("MissingPermission")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        //Gettin last location
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.requireActivity())
        fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    viewModel.longattitude = location!!.longitude.toString()
                    viewModel.latitude = location!!.latitude.toString()
                }


        val binding = DataBindingUtil.inflate<FragmentOverviewBinding>(inflater, R.layout.fragment_overview, container, false)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel
        binding.restaurantGrid.adapter = RestaurantGridAdapter(RestaurantGridAdapter.OnClickListener{
            viewModel.displayRestaurantDetails(it)
        })

        viewModel.navigateToSelectednearbyRestaurant.observe(viewLifecycleOwner, Observer {
            if (it != null){
                this.findNavController().navigate(OverviewFragmentDirections.actionOverviewFragmentToDetailFragment(it))
                viewModel.displayRestaurantDetailsComplete()
            }
        })

        setHasOptionsMenu(true)
        return binding.root
}


}