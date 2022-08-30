package com.example.footballsimulator.common.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.footballsimulator.common.data.db.entities.DbFixture

@Dao
interface FixturesDao {

    @Query("SELECT * FROM fixtures_table")
    suspend fun fetchFixtures(): List<DbFixture>
}
