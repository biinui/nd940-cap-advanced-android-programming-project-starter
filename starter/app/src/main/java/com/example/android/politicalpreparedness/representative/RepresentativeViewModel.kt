package com.example.android.politicalpreparedness.representative

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.network.CivicsApi
import kotlinx.coroutines.launch
import timber.log.Timber

class RepresentativeViewModel: ViewModel() {

    //TODO: Establish live data for representatives and address

    //TODO: Create function to fetch representatives from API from a provided address
    init {
        getRepresentatives()
    }

    private fun getRepresentatives() {
        viewModelScope.launch {
            val dummyAddress = "Modesto"
            val representativeResponse = CivicsApi.retrofitService.getRepresentatives(dummyAddress)
            Timber.i("getVoterInfo.election: ${representativeResponse.offices}")
            Timber.i("getVoterInfo.election: ${representativeResponse.officials}")
        }
    }

    /**
     *  The following code will prove helpful in constructing a representative from the API. This code combines the two nodes of the RepresentativeResponse into a single official :

    val (offices, officials) = getRepresentativesDeferred.await()
    _representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }

    Note: getRepresentatives in the above code represents the method used to fetch data from the API
    Note: _representatives in the above code represents the established mutable live data housing representatives

     */

    //TODO: Create function get address from geo location

    //TODO: Create function to get address from individual fields

}
