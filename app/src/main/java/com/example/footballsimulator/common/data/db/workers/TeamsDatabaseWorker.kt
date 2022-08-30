package com.example.footballsimulator.common.data.db.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.footballsimulator.common.data.db.dao.TeamsDao
import com.example.footballsimulator.common.data.db.entities.DbTeam
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class TeamsDatabaseWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val teamsDao: TeamsDao //TODO replace with repository
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        teamsDao.insertAll(mockTeams)

        return Result.success()
    }
}

/*
 * these are the teams that I got from the players.json.
 * In the real world this should already come from some API (didn't have the time to implement it here
  */
private val mockTeams = listOf(
    DbTeam(name = "Arsenal", numberOfGoalsScoredLastSeason = 62, numberOfGoalsConcededLastSeason = 48),
    DbTeam(name = "Chelsea", numberOfGoalsScoredLastSeason = 76, numberOfGoalsConcededLastSeason = 33),
    DbTeam(name = "Crystal Palace", numberOfGoalsScoredLastSeason = 50, numberOfGoalsConcededLastSeason = 46),
    DbTeam(name = "Leicester", numberOfGoalsScoredLastSeason = 62, numberOfGoalsConcededLastSeason = 59),
    DbTeam(name = "Manchester City", numberOfGoalsScoredLastSeason = 99, numberOfGoalsConcededLastSeason = 26),
    DbTeam(name = "Manchester United", numberOfGoalsScoredLastSeason = 57, numberOfGoalsConcededLastSeason = 57),
    DbTeam(name = "Stoke", numberOfGoalsScoredLastSeason = 30, numberOfGoalsConcededLastSeason = 65),
    DbTeam(name = "Tottenham", numberOfGoalsScoredLastSeason = 69, numberOfGoalsConcededLastSeason = 40),
    DbTeam(name = "West Brom", numberOfGoalsScoredLastSeason = 23, numberOfGoalsConcededLastSeason = 67),
)
