package com.example.footballsimulator.common.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.footballsimulator.common.data.db.entities.DbTeam

@Dao
interface TeamsDao {

    @Query("SELECT * FROM teams_table")
    suspend fun fetchTeams(): List<DbTeam>

}