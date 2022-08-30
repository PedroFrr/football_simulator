package com.example.footballsimulator.standings.domain

import com.example.footballsimulator.common.data.db.dao.FixturesDao
import com.example.footballsimulator.common.data.db.dao.TeamsDao
import com.example.footballsimulator.teams.domain.Team
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StandingsRepositoryImpl @Inject constructor(
    private val fixturesDao: FixturesDao,
    private val teamsDao: TeamsDao,
) : StandingsRepository {

    override suspend fun getStandings(): Flow<List<TeamStanding>> = flow {
        while (true) {
            val fixtures = fixturesDao.fetchFixtures()
            val teamsInFixtures = fixtures.map {
                teamsDao.getTeamById(it.homeTeamId)
            }.distinct()

            emit(teamsInFixtures.map { team ->
                val teamFixtures = fixturesDao.fetchTeamFixtures(team.teamId)
                val goalsScored = teamFixtures.sumOf {
                    if (it.homeTeamId == team.teamId) it.homeTeamScore ?: 0 else it.awayTeamScore ?: 0
                }
                val goalsConceded = teamFixtures.sumOf {
                    if (it.homeTeamId == team.teamId) it.awayTeamScore ?: 0 else it.homeTeamScore ?: 0
                }

                TeamStanding(
                    standingPosition = 0,
                    goalsScored = goalsScored,
                    goalsConceded = goalsConceded,
                    team = Team(
                        teamId = team.teamId,
                        name = team.name,
                    ),
                )
            })
        }
    }
}