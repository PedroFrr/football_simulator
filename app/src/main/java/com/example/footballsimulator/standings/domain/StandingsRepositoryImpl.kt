package com.example.footballsimulator.standings.domain

import com.example.footballsimulator.common.data.db.dao.FixturesDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StandingsRepositoryImpl @Inject constructor(
    private val fixturesDao: FixturesDao,
) : StandingsRepository {

    override suspend fun getStandings(): Flow<List<TeamStanding>> = flow {
        while (true) {
            val standings = fixturesDao.fetchTeamStandings()

            val teamStandings = standings.map {
                TeamStanding(
                    teamName = it.teamName,
                    matchesPlayed = it.matchesPlayed,
                    wins = it.wins,
                    draws = it.draws,
                    losses = it.losses,
                    goalsScored = it.goalsScored,
                    goalsConceded = it.goalsConceded,
                    goalDifference = it.goalDifference,
                    points = it.points
                )
            }

            emit(teamStandings)
        }
    }
}
