package com.example.footballsimulator.standings

import com.example.footballsimulator.standings.domain.TeamStanding
import java.util.UUID

sealed class TeamStandingDataItem {
    abstract val id: String

    data class TeamStandingItem(val teamStanding: TeamStanding) : TeamStandingDataItem() {
        override val id = teamStanding.team.teamId
    }

    object Header : TeamStandingDataItem() {
        override val id = UUID.randomUUID().toString()
    }
}
