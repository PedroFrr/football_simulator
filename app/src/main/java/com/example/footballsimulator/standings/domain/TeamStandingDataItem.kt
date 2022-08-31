package com.example.footballsimulator.standings.domain

import java.util.UUID

sealed class TeamStandingDataItem {
    abstract val id: String

    data class TeamStandingItem(val teamStanding: TeamStanding) : TeamStandingDataItem() {
        override val id = teamStanding.teamName
    }

    object Header : TeamStandingDataItem() {
        override val id = UUID.randomUUID().toString()
    }
}
