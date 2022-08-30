package com.example.footballsimulator.common.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.footballsimulator.common.data.db.entities.DbPlayer
import com.example.footballsimulator.common.data.db.entities.DbTeam

@Dao
interface PlayersDao {

    @Query("SELECT * FROM players_table")
    suspend fun fetchPlayers(): List<DbPlayer>

    @Query(
        "SELECT * FROM players_table" +
            " JOIN teams_table ON teams_table.teamId == players_table.teamId"
    )
    suspend fun getTeamPlayers(): Map<DbTeam, List<DbPlayer>>
}
