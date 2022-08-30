package com.example.footballsimulator.teams.domain

import java.util.*

data class Team(
    val teamId: String = UUID.randomUUID().toString(),
    val name: String
)
