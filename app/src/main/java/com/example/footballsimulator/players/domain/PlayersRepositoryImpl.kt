package com.example.footballsimulator.players.domain

import com.example.footballsimulator.common.data.db.dao.PlayersDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayersRepositoryImpl @Inject constructor(
    private val playersDao: PlayersDao
) : PlayersRepository {
    override fun getTeamPlayers(teamId: String): Flow<List<Player>> = playersDao.getTeamPlayersStream(teamId).map {
        it.map {
            Player(
                playerId = it.playerId,
                teamId = it.teamId,
                name = it.name
            )
        }
    }
}