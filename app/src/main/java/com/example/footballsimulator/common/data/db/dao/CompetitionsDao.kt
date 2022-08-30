package com.example.footballsimulator.common.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.footballsimulator.common.data.db.entities.DbCompetition

@Dao
interface CompetitionsDao {

    @Query("SELECT * FROM competitions_table")
    suspend fun fetchCompetitions(): List<DbCompetition>
}
