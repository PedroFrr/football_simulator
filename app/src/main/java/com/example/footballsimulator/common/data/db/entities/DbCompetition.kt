package com.example.footballsimulator.common.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "competitions_table")
data class DbCompetition(
    @PrimaryKey val competitionId: String = UUID.randomUUID().toString(),
    val name: String,
    val season: String // year of the season
)
