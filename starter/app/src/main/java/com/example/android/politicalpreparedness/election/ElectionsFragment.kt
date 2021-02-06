package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.election.adapter.ElectionListener
import com.example.android.politicalpreparedness.repository.ElectionRepository

class ElectionsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val repository = ElectionRepository(ElectionDatabase.getInstance(requireContext()))

        val viewModelFactory = ElectionsViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(ElectionsViewModel::class.java)

        val binding = FragmentElectionBinding.inflate(inflater, container, false)

        val upcomingElectionListAdapter = ElectionListAdapter(ElectionListener { election ->
            viewModel.navigateToVoterInfoAbout(election)
        })
        binding.upcomingElectionList.adapter = upcomingElectionListAdapter
        viewModel.upcomingElections.observe(viewLifecycleOwner, Observer { electionList ->
            electionList?.let {
                upcomingElectionListAdapter.submitList(electionList)
            }
        })

        viewModel.navigateToVoterInfo.observe(viewLifecycleOwner, Observer { election ->
            election?.let {
                findNavController().navigate(ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(election.id, election.division))
                viewModel.navigationToVoterInfoDone()
            }
        })

        return binding.root
    }

}