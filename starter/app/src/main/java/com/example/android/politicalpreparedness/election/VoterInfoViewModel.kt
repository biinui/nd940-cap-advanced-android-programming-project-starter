package com.example.android.politicalpreparedness.election

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.State
import com.example.android.politicalpreparedness.repository.ElectionRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class VoterInfoViewModel(private val repository: ElectionRepository) : ViewModel() {

    //TODO: Add live data to hold voter info
    private var _election = MutableLiveData<Election>()
    val election: LiveData<Election>
        get() = _election

    private var _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    private var _electionInfoUrl = MutableLiveData<String>()
    val electionInfoUrl: LiveData<String>
        get() = _electionInfoUrl

    private var _votingLocationFinderUrl = MutableLiveData<String>()
    val votingLocationFinderUrl: LiveData<String>
        get() = _votingLocationFinderUrl

    private var _ballotInfoUrl = MutableLiveData<String>()
    val ballotInfoUrl: LiveData<String>
        get() = _ballotInfoUrl

    init {
        getVoterInfo()
    }

    //TODO: Add var and methods to populate voter info
    private fun getVoterInfo() {
        viewModelScope.launch {
            try {
                val dummyAddress = "Modesto"
                val dummyElectionId = 2000
                val voterResponse = CivicsApi.retrofitService.getVoterInfo(dummyAddress, dummyElectionId)
                _state.value = voterResponse.state?.get(0)
                _election.value = voterResponse.election
                Timber.i("getVoterInfo.election: ${voterResponse.election}")
                Timber.i("getVoterInfo.state: ${voterResponse.state}")
            } catch (e: Exception) {
                Timber.e("getVoterInfo.exception: ${e.localizedMessage}")
            }
        }
    }

    //TODO: Add var and methods to support loading URLs
    fun openElectionInfoUrl() {
        _electionInfoUrl.value = _state.value?.electionAdministrationBody?.electionInfoUrl
    }

    fun openElectionInfoUrlDone() {
        _electionInfoUrl.value = null
    }

    fun openVotingLocationFinderUrl() {
        _votingLocationFinderUrl.value = _state.value?.electionAdministrationBody?.votingLocationFinderUrl
    }

    fun openVotingLocationFinderUrlDone() {
        _votingLocationFinderUrl.value = null
    }

    fun openBallotInfoUrl() {
        _ballotInfoUrl.value = _state.value?.electionAdministrationBody?.ballotInfoUrl
    }

    fun openBallotInfoUrlDone() {
        _ballotInfoUrl.value = null
    }

    //TODO: Add var and methods to save and remove elections to local database
    //TODO: cont'd -- Populate initial state of save button to reflect proper action based on election saved status

    /**
     * Hint: The saved state can be accomplished in multiple ways. It is directly related to how elections are saved/removed from the database.
     */

}