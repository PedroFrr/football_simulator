package com.example.footballsimulator.common.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "fixtures_table")
data class DbFixture(
    @PrimaryKey val fixtureId: String = UUID.randomUUID().toString(),
    val name: String
)
