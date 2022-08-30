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
    fun fetchFixtures(): Flow<List<DbFixture>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(fixtures: List<DbFixture>)
}
