package com.example.footballsimulator.standings.domain

import com.example.footballsimulator.common.data.db.dao.FixturesDao
import com.example.footballsimulator.common.data.db.dao.TeamsDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StandingsRepositoryImpl @Inject constructor(
    private val fixturesDao: FixturesDao,
    private val teamsDao: TeamsDao,
) : StandingsRepository {

    override suspend fun getStandings(): Flow<List<TeamStanding>> {
        val fixtures = fixturesDao.fetchFixtures()
        val teams = teamsDao.fetchTeams()

    }
}