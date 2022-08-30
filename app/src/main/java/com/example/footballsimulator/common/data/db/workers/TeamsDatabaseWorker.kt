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
class TeamsWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val teamsDao: TeamsDao //TODO replace with repository
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        //TODO generate the first batch of fixtures

        //fixturesDao.insertAll(fixtures)

        return Result.success()
    }
}

private val mockTeams = listOf(
    DbTeam(name = "Arsenal"),
    DbTeam(name = "United"),
    DbTeam(name = "Chelsea"),
    DbTeam(name = "Leeds"),
)
