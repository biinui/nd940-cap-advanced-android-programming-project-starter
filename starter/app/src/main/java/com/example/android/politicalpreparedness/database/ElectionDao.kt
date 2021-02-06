package com.example.android.politicalpreparedness.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.android.politicalpreparedness.network.models.Election

@Dao
interface ElectionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertElections(election: List<Election>)

    @Query("SELECT * FROM Election")
    fun getElections(): LiveData<List<Election>>

    @Query("SELECT * FROM Election WHERE election.id = :id")
    fun getElectionById(id: Int): LiveData<Election>

    //TODO: Add delete query

    //TODO: Add clear query

}