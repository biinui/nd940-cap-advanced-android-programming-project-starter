package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.election.adapter.ElectionListener

class ElectionsFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val viewModelFactory = ElectionsViewModelFactory()
        val viewModel = ViewModelProvider(this, viewModelFactory).get(ElectionsViewModel::class.java)

        val binding = FragmentElectionBinding.inflate(inflater, container, false)

        //TODO: Initiate recycler adapters
        val upcomingElectionListAdapter = ElectionListAdapter( ElectionListener { electionId ->
            //TODO: Link elections to voter info
            viewModel.navigateToVoterInfoWith(electionId)
        })

        viewModel.navigateToVoterInfo.observe(viewLifecycleOwner, Observer { electionId ->
//            findNavController().navigate(ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(electionId, null))
            viewModel.navigationToVoterInfoDone()
        })

        //TODO: Populate recycler adapters
        binding.upcomingElectionList.adapter = upcomingElectionListAdapter
        viewModel.upcomingElections.observe(viewLifecycleOwner, Observer {
            it?.let {
                upcomingElectionListAdapter.submitList(it)
            }
        })

        return binding.root
    }

    //TODO: Refresh adapters when fragment loads

}