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

    private val _upcomingElections = MutableLiveData<List<Election>>()
    val upcomingElections: LiveData<List<Election>>
        get() = _upcomingElections

    private val _navigateToVoterInfo = MutableLiveData<Election>()
    val navigateToVoterInfo: LiveData<Election>
        get() = _navigateToVoterInfo

    //TODO: Create live data val for saved elections

    init {
        _upcomingElections.value = emptyList()
        getElections()
    }

    //TODO: save elections to the local database
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

    fun navigateToVoterInfoWith(election: Election) {
        _navigateToVoterInfo.value = election
    }

    fun navigationToVoterInfoDone() {
        _navigateToVoterInfo.value = null
    }

    //TODO: Create functions to navigate to saved election voter info

}