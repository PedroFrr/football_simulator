package com.example.footballsimulator.common.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "competition_phase_table")
data class DbCompetitionPhase(
    @PrimaryKey val competitionPhaseId: String = UUID.randomUUID().toString(),
    val name: String
)