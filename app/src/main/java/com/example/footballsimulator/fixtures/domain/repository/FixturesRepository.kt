package com.example.footballsimulator.fixtures.domain.repository

import com.example.footballsimulator.fixtures.domain.Fixture
import kotlinx.coroutines.flow.Flow

interface FixturesRepository {
    fun getFixtures(): Flow<List<Fixture>>

    suspend fun simulateMatches()
}
