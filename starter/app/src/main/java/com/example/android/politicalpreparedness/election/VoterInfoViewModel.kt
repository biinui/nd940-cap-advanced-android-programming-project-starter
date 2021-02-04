package com.example.android.politicalpreparedness.election

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.repository.ElectionRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception

class VoterInfoViewModel(private val repository: ElectionRepository) : ViewModel() {

    init {
        getVoterInfo()
    }

    //TODO: Add live data to hold voter info

    //TODO: Add var and methods to populate voter info
    private fun getVoterInfo() {
        viewModelScope.launch {
            try {
                val dummyAddress = Address("", "", "Modesto", "CA", "")
                val dummyElectionId = 2000
                val voterResponse = CivicsApi.retrofitService.getVoterInfo(dummyAddress, dummyElectionId)
                Timber.i("getVoterInfo.election: ${voterResponse.election}")
                Timber.i("getVoterInfo.state: ${voterResponse.state}")
            } catch (e: Exception) {
                Timber.e("getVoterInfo.exception: ${e.localizedMessage}")
            }
        }
    }

    //TODO: Add var and methods to support loading URLs

    //TODO: Add var and methods to save and remove elections to local database
    //TODO: cont'd -- Populate initial state of save button to reflect proper action based on election saved status

    /**
     * Hint: The saved state can be accomplished in multiple ways. It is directly related to how elections are saved/removed from the database.
     */

}