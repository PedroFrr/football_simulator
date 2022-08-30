package com.example.footballsimulator.common.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "players_table")
data class DbPlayer(
    @PrimaryKey val playerId: String = UUID.randomUUID().toString(),
    val teamId: String,
    val name: String,
    val offense: Int,
    val defence: Int,
    val agility: Int,
    val position: String
    // TODO add other attributes if needed
)