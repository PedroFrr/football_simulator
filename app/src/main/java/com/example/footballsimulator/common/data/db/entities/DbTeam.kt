package com.example.footballsimulator.common.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "teams_table")
data class DbTeam(
    @PrimaryKey val teamId: String = UUID.randomUUID().toString(),
    val name: String,
    val numberOfGoalsScoredLastSeason: Int,
    val numberOfGoalsConcededLastSeason: Int
)
