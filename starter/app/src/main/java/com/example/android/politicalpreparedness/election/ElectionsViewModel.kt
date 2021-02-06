package com.example.android.politicalpreparedness.election

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.repository.ElectionRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception

class ElectionsViewModel(private val repository: ElectionRepository): ViewModel() {

    val upcomingElections = repository.elections

    private val _navigateToVoterInfo = MutableLiveData<Election>()
    val navigateToVoterInfo: LiveData<Election>
        get() = _navigateToVoterInfo

    //TODO: Create live data val for saved elections

    init {
        refreshElections()
    }

    private fun refreshElections() {
        viewModelScope.launch {
            try {
                repository.refreshElections()
            } catch (e: Exception) {
                // TODO show toast
                Timber.i("refreshElections: ${e.localizedMessage}")
            }
        }
    }

    fun navigateToVoterInfoAbout(election: Election) {
        _navigateToVoterInfo.value = election
    }

    fun navigationToVoterInfoDone() {
        _navigateToVoterInfo.value = null
    }

}