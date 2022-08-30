package com.example.footballsimulator.common.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.footballsimulator.common.data.db.entities.DbTeam
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamsDao {

    @Query("SELECT * FROM teams_table")
    fun fetchTeams(): Flow<List<DbTeam>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(teams: List<DbTeam>)

    @Query("SELECT teamId FROM teams_table WHERE name = :teamName LIMIT 1")
    suspend fun getTeamIdBasedOnName(teamName: String): String

    @Query("SELECT * FROM teams_table WHERE teamId = :teamId LIMIT 1")
    suspend fun getTeamById(teamId: String): DbTeam
}
