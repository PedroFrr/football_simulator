package com.example.footballsimulator.standings.domain

data class TeamStanding(
    val teamName: String,
    val matchesPlayed: Int,
    val wins: Int,
    val draws: Int,
    val losses: Int,
    val goalsScored: Int,
    val goalsConceded: Int,
    val goalDifference: Int,
    val points: Int,
)
