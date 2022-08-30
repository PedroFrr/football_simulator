package com.example.footballsimulator.teams.domain

import com.example.footballsimulator.common.data.db.dao.TeamsDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TeamsRepositoryImpl @Inject constructor(
    private val teamsDao: TeamsDao
) : TeamsRepository {
    override fun getTeams(): Flow<List<Team>> = teamsDao.fetchTeamsStream().map {
        it.map {
            Team(
                teamId = it.teamId,
                name = it.name
            )
        }
    }
}