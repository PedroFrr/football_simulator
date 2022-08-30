package com.example.footballsimulator.fixtures.domain.repository

import com.example.footballsimulator.common.data.db.dao.FixturesDao
import com.example.footballsimulator.common.data.db.dao.TeamsDao
import com.example.footballsimulator.fixtures.domain.Fixture
import com.example.footballsimulator.teams.domain.Team
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FixturesRepositoryImpl @Inject constructor(
    private val fixturesDao: FixturesDao,
    private val teamsDao: TeamsDao
) : FixturesRepository {
    override fun getFixtures(): Flow<List<Fixture>> = fixturesDao.fetchFixtures().map {
        it.map {
            val homeTeam = teamsDao.getTeamById(it.homeTeamId)
            val awayTeam = teamsDao.getTeamById(it.awayTeamId)
            Fixture(
                date = it.date,
                homeTeamScore = it.homeTeamScore,
                homeTeam = Team(
                    teamId = homeTeam.teamId,
                    name = homeTeam.name
                ),
                awayTeamScore = it.awayTeamScore,
                awayTeam = Team(
                    teamId = awayTeam.teamId,
                    name = awayTeam.name
                ),
            )
        }
    }
}