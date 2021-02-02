package com.example.android.politicalpreparedness.election

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.launch
import timber.log.Timber

//TODO: Construct ViewModel and provide election datasource
class ElectionsViewModel: ViewModel() {

    //TODO: Create live data val for upcoming elections
    private val _upcomingElections = MutableLiveData<List<Election>>()
    val upcomingElections: LiveData<List<Election>>
        get() = _upcomingElections

    private val _navigateToVoterInfo = MutableLiveData<Int>()
    val navigateToVoterInfo: LiveData<Int>
        get() = _navigateToVoterInfo

    //TODO: Create live data val for saved elections

    init {
        _upcomingElections.value = emptyList()
        getElections()
    }

    //TODO: Create val and functions to populate live data for upcoming elections from the API and saved elections from local database
    private fun getElections() {
        viewModelScope.launch {
            try {
                val electionResponse = CivicsApi.retrofitService.getElections()
                _upcomingElections.value = electionResponse.elections
                Timber.i("result: $electionResponse.")
            } catch (e: Exception) {
                Timber.e("getElections.onFailure: ${e.localizedMessage}")
            }
        }
    }

    //TODO: Create functions to navigate to saved or upcoming election voter info
    fun navigateToVoterInfoWith(electionId: Int) {
        _navigateToVoterInfo.value = electionId
    }

    fun navigationToVoterInfoDone() {
        _navigateToVoterInfo.value = null
    }

}