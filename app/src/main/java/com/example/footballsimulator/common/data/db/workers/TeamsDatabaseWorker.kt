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
    DbTeam(name = "Arsenal"),
    DbTeam(name = "Chelsea"),
    DbTeam(name = "Crystal Palace"),
    DbTeam(name = "Leicester"),
    DbTeam(name = "Manchester City"),
    DbTeam(name = "Manchester United"),
    DbTeam(name = "Stoke"),
    DbTeam(name = "Tottenham"),
    DbTeam(name = "West Brom"),
)
