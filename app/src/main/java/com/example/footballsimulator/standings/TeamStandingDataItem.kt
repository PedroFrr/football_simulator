package com.example.footballsimulator.standings

import com.example.footballsimulator.standings.domain.TeamStanding

sealed class TeamStandingDataItem {
    abstract val id: Int

    data class TeamStandingItem(val teamStanding: TeamStanding) : TeamStandingDataItem() {
        override val id = teamStanding.standingPosition
    }

    object Header : TeamStandingDataItem() {
        override val id = Int.MAX_VALUE
    }
}
