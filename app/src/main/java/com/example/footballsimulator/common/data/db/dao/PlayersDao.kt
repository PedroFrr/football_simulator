package com.example.footballsimulator.common.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.footballsimulator.common.data.db.entities.DbPlayer
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayersDao {

    @Query("SELECT * FROM players_table")
    suspend fun fetchPlayers(): List<DbPlayer>

    @Query(
        "SELECT * FROM players_table" +
                " WHERE teamId == :teamId"
    )
    fun getTeamPlayersStream(teamId: String): Flow<List<DbPlayer>>

    @Query(
        "SELECT * FROM players_table" +
                " WHERE teamId == :teamId"
    )
    suspend fun getTeamPlayers(teamId: String): List<DbPlayer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(fixtures: List<DbPlayer>)
}
