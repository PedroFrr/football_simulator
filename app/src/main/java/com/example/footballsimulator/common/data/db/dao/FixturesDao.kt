package com.example.footballsimulator.common.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.footballsimulator.common.data.db.entities.DbFixture
import kotlinx.coroutines.flow.Flow

@Dao
interface FixturesDao {

    @Query("SELECT * FROM fixtures_table")
    fun fetchFixturesStream(): Flow<List<DbFixture>>

    @Query("SELECT * FROM fixtures_table")
    suspend fun fetchFixtures(): List<DbFixture>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(fixtures: List<DbFixture>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFixtures(fixtures: List<DbFixture>)

    @Query("SELECT * FROM fixtures_table WHERE homeTeamId == :teamId OR awayTeamId == :teamId")
    suspend fun fetchTeamFixtures(teamId: String): List<DbFixture>

    @Query("SELECT * FROM fixtures_table WHERE homeTeamId == :teamId OR awayTeamId == :teamId")
    suspend fun fetchTeamFixturesTest(teamId: String): List<DbFixture>
}
