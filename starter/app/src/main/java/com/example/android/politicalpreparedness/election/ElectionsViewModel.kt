package com.example.android.politicalpreparedness.election

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.network.CivicsApi
import kotlinx.coroutines.launch
import timber.log.Timber

//TODO: Construct ViewModel and provide election datasource
class ElectionsViewModel: ViewModel() {

    init {
        getElections()
    }

    //TODO: Create live data val for upcoming elections

    //TODO: Create live data val for saved elections

    //TODO: Create val and functions to populate live data for upcoming elections from the API and saved elections from local database
    private fun getElections() {
        viewModelScope.launch {
            try {
                val electionResponse = CivicsApi.retrofitService.getElections()
                Timber.i("result: $electionResponse.")
            } catch (e: Exception) {
                Timber.e("getElections.onFailure: ${e.localizedMessage}")
            }
        }
    }

    //TODO: Create functions to navigate to saved or upcoming election voter info

}