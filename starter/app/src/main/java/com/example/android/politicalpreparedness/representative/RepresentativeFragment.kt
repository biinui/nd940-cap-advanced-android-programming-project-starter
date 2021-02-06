package com.example.android.politicalpreparedness.representative

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.FragmentRepresentativeBinding
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.representative.adapter.RepresentativeListAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.fragment_representative.*
import java.util.*


class DetailFragment : Fragment() {

    companion object {
        const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        val viewModel = ViewModelProvider(this).get(RepresentativeViewModel::class.java)

        val binding = FragmentRepresentativeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        val representativeListAdapter = RepresentativeListAdapter()
        binding.representativeList.adapter = representativeListAdapter

        viewModel.representativeList.observe(viewLifecycleOwner, Observer { representativeList ->
            representativeList?.let {
                representativeListAdapter.submitList(representativeList)
            }
        })

        //TODO: Establish button listeners for field and location search
        binding.useMyLocationButton.setOnClickListener {
            getLocation()
        }

        populateStateSpinner(binding)

        return binding.root
    }

    private fun populateStateSpinner(binding: FragmentRepresentativeBinding) {
        val spinner: Spinner = binding.stateSpinner
        ArrayAdapter.createFromResource(
                requireContext(),
                R.array.states,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode != PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) return

        if (isPermissionGranted()) {
            getLocation()
        } else {
            // TODO Permission was denied. Display an error message
            //      Display the missing permission error dialog when the fragments resume.
        }
    }

    private fun requestFineLocationPermission() {
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        if (isPermissionGranted()) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    val address = geoCodeLocation(location)
                    address_line_1_edit.setText(address.line1)
                    address_line_2_edit.setText(address.line2)
                    city_edit.setText(address.city)
                    val states = resources.getStringArray(R.array.states)
                    val index = states.indexOf(address.state)
                    state_spinner.setSelection(index)
                    zip_edit.setText(address.zip)
                }
            }
        } else {
            requestFineLocationPermission()
        }
    }

    private fun isPermissionGranted() : Boolean {
        return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun geoCodeLocation(location: Location): Address {
        val geocoder = Geocoder(context, Locale.getDefault())
        return geocoder.getFromLocation(location.latitude, location.longitude, 1)
                .map { address ->
                    Address(address.thoroughfare, address.subThoroughfare, address.locality, address.adminArea, address.postalCode)
                }
                .first()
    }

    private fun hideKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view!!.windowToken, 0)
    }

}