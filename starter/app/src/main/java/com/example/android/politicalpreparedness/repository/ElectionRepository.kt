package com.example.android.politicalpreparedness.repository

import android.app.Application
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.database.ElectionDatabase

class ElectionRepository(private val application: Application) {
    private val db = ElectionDatabase.getInstance(application)
    private val dao = db.electionDao


}