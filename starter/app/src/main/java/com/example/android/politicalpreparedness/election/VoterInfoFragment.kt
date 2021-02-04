package com.example.android.politicalpreparedness.election

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding
import com.example.android.politicalpreparedness.repository.ElectionRepository

class VoterInfoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val repository = ElectionRepository(requireActivity().application)
        //TODO: Add ViewModel values and create ViewModel
        val viewModelFactory = VoterInfoViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(VoterInfoViewModel::class.java)

        val binding = FragmentVoterInfoBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        //TODO: Populate voter info -- hide views without provided data.
        /**
        Hint: You will need to ensure proper data is provided from previous fragment.
        */

        //TODO: Handle loading of URLs
        viewModel.electionInfoUrl.observe(viewLifecycleOwner, Observer { urlStr ->
            urlStr?.let {
                startActivityWithUrlIntentUsing(urlStr)
                viewModel.openElectionInfoUrlDone()
            }
        })

        viewModel.votingLocationFinderUrl.observe(viewLifecycleOwner, Observer { urlStr ->
            urlStr?.let {
                startActivityWithUrlIntentUsing(urlStr)
                viewModel.openVotingLocationFinderUrlDone()
            }
        })

        viewModel.ballotInfoUrl.observe(viewLifecycleOwner, Observer { urlStr ->
            urlStr?.let {
                startActivityWithUrlIntentUsing(urlStr)
                viewModel.openBallotInfoUrlDone()
            }
        })

        //TODO: Handle save button UI state
        //TODO: cont'd Handle save button clicks

        return binding.root
    }

    //TODO: Create method to load URL intents
    private fun startActivityWithUrlIntentUsing(urlStr: String) {
        val uri: Uri = Uri.parse(urlStr)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

}