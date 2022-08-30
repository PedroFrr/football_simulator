package com.example.footballsimulator.common.data.db.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.footballsimulator.R
import com.example.footballsimulator.common.data.db.dao.PlayersDao
import com.example.footballsimulator.common.data.db.entities.DbPlayer
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class PlayersDatabaseWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val playersDao: PlayersDao //TODO replace with repository
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        //TODO generate the first batch of fixtures

        val jsonString = appContext.resources.openRawResource(R.raw.players).bufferedReader().use {
            it.readText()
        }
        val moshi: Moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, PlayerWorker::class.java)
        val jsonAdapter: JsonAdapter<List<PlayerWorker>> = moshi.adapter(type)
        val players = jsonAdapter.fromJson(jsonString)!!.map {
            DbPlayer(
                teamId = it.team,
                name = "${it.firstName} ${it.lastName}",
                offense = 0,
                defence = 0,
                agility = 0,
                position = it.position
            )
        }
        // 3
        playersDao.insertAll(players)

        return Result.success()
    }
}
