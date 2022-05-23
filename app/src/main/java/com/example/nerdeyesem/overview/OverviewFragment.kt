package com.example.nerdeyesem.overview

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.app.ActivityCompat
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

    private val LOCATION_PERMISSION_REQ_CODE = 999;
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val viewModel: OverviewViewModel by lazy { ViewModelProvider(this).get(OverviewViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.requireActivity())
        getCurrentLocation()
    }

    @SuppressLint("MissingPermission")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

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

    private fun getCurrentLocation() {
        // checking location permission
        if (ActivityCompat.checkSelfPermission(this.requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // request permission
            ActivityCompat.requestPermissions(this.requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQ_CODE);
            return
        }
        fusedLocationClient.lastLocation?.let {
            it
                    .addOnSuccessListener { location ->
                        viewModel.getZomatoProperties(location.longitude.toString(), location.latitude.toString())

                    }
                    .addOnFailureListener {
                        Toast.makeText(this.requireContext(), "Failed on getting current location",
                                Toast.LENGTH_SHORT).show()
                    }
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        when (requestCode) {
            LOCATION_PERMISSION_REQ_CODE -> {
                if (grantResults.isNotEmpty() &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted
                } else {
                    // permission denied
                    Toast.makeText(this.requireContext(), "You need to grant permission to access location",
                            Toast.LENGTH_SHORT).show()
                }
            }
        }
    }



}