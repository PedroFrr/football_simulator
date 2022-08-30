package com.example.footballsimulator.common.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.footballsimulator.common.data.db.entities.DbTeam

@Dao
interface TeamsDao {

    @Query("SELECT * FROM teams_table")
    suspend fun fetchTeams(): List<DbTeam>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(teams: List<DbTeam>)

    @Query("SELECT teamId FROM teams_table WHERE name = :teamName LIMIT 1")
    suspend fun getTeamIdBasedOnName(teamName: String): String
}
