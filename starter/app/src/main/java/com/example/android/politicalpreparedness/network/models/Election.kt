package com.example.android.politicalpreparedness.network.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import java.util.*

@Entity
data class Election( @PrimaryKey
                     val id: Int
                   , val name: String
                   , val electionDay: Date
                   , @Embedded(prefix = "division_")
                     @Json(name="ocdDivisionId")
                     val division: Division
                   , val saved: Boolean = false
)