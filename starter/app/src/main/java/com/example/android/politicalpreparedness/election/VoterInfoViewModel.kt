package com.example.android.politicalpreparedness.election

import androidx.lifecycle.*
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.repository.VoterInfoRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class VoterInfoViewModel( private val repository: VoterInfoRepository
                        , val election: Election
                        ) : ViewModel() {

    val state = repository.state

    private val savedElection = repository.savedElection
    var followButtonText = Transformations.map(savedElection) {
        if (it == null) {
            "Follow"
        } else {
            "Unfollow"
        }
    }

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

    private fun getVoterInfo() {
        viewModelScope.launch {
            try {
                val dummyAddress = "Modesto"
                repository.refreshVoterInfo(dummyAddress, election.id)
                repository.getElectionById(election.id)
            } catch (e: Exception) {
                // TODO show toast
                Timber.e("getVoterInfo.exception: ${e.localizedMessage}")
            }
        }
    }

    fun openElectionInfoUrl() {
        _electionInfoUrl.value = state.value?.electionAdministrationBody?.electionInfoUrl
    }

    fun openElectionInfoUrlDone() {
        _electionInfoUrl.value = null
    }

    fun openVotingLocationFinderUrl() {
        _votingLocationFinderUrl.value = state.value?.electionAdministrationBody?.votingLocationFinderUrl
    }

    fun openVotingLocationFinderUrlDone() {
        _votingLocationFinderUrl.value = null
    }

    fun openBallotInfoUrl() {
        _ballotInfoUrl.value = state.value?.electionAdministrationBody?.ballotInfoUrl
    }

    fun openBallotInfoUrlDone() {
        _ballotInfoUrl.value = null
    }

    //TODO: Add var and methods to save and remove elections to local database
    //TODO: cont'd -- Populate initial state of save button to reflect proper action based on election saved status

    /**
     * Hint: The saved state can be accomplished in multiple ways. It is directly related to how elections are saved/removed from the database.
     */

    fun toggleFollow() {
        viewModelScope.launch {
            repository.saveElection(election)
        }
    }

    fun unfollowElection() {
        viewModelScope.launch {
            repository.deleteElection(election)
        }
    }

}