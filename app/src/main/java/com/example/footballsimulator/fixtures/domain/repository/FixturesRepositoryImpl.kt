package com.example.footballsimulator.fixtures.domain.repository

import com.example.footballsimulator.common.data.db.dao.FixturesDao
import com.example.footballsimulator.fixtures.domain.Fixture
import com.example.footballsimulator.teams.domain.Team
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FixturesRepositoryImpl @Inject constructor(
    private val fixturesDao: FixturesDao
) : FixturesRepository {
    override fun getFixtures(): Flow<List<Fixture>> = fixturesDao.fetchFixtures().map {
        it.map {
            Fixture(
                date = it.date,
                homeTeamScore = it.homeTeamScore ?: 0,
                homeTeam = Team(name = "Arsenal"),
                awayTeamScore = it.awayTeamScore ?: 0,
                awayTeam = Team(name = "United")
            )
        }
    }
}