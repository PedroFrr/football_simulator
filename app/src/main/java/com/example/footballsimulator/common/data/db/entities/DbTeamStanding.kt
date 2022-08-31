package com.example.footballsimulator.common.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class DbTeamStanding(
    @ColumnInfo(name = "Club") val teamName: String,
    @ColumnInfo(name = "MP") val matchesPlayed: Int,
    @ColumnInfo(name = "W") val wins: Int,
    @ColumnInfo(name = "D") val draws: Int,
    @ColumnInfo(name = "L") val losses: Int,
    @ColumnInfo(name = "GF") val goalsScored: Int,
    @ColumnInfo(name = "GA") val goalsConceded: Int,
    @ColumnInfo(name = "GD") val goalDifference: Int,
    @ColumnInfo(name = "Pts") val points: Int,
)
