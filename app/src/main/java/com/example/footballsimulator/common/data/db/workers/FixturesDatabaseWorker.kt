package com.example.footballsimulator.common.data.db.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.footballsimulator.common.data.db.dao.FixturesDao
import com.example.footballsimulator.common.data.db.entities.DbFixture
import com.example.footballsimulator.common.data.db.entities.DbTeam
import com.example.footballsimulator.common.util.YYYY_MM_DD
import com.example.footballsimulator.common.util.formatToPattern
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.LocalDate
import java.time.Month

@HiltWorker
class FixturesDatabaseWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val fixturesDao: FixturesDao //TODO replace with repository
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        //TODO generate the first batch of fixtures

        val fixtures = generateFixtures(mockTeams)

        fixturesDao.insertAll(fixtures)

        return Result.success()
    }

    private fun generateFixtures(teams: List<DbTeam>): List<DbFixture> {
        val tournamentStartDate = LocalDate.of(2022, Month.JULY, 30)
        val numTeams = teams.size
        val numDays: Int = (numTeams - 1) * 2 // Days needed to complete tournament
        val halfSize: Int = numTeams / 2
        val teamsList = mutableListOf<DbTeam>()
        val fixtures = mutableListOf<DbFixture>()
        teamsList.addAll(teams) // Add teams to List and remove the first team
        teamsList.removeAt(0)
        val teamsSize: Int = teams.size
        var fixtureDate = tournamentStartDate.plusDays(1)
        for (day in 0 until numDays) {
            val teamIdx = day % teamsSize
            if (day <= halfSize) {
                fixtures.add(
                    DbFixture(
                        date = fixtureDate.formatToPattern(YYYY_MM_DD),
                        homeTeamId = teams[teamIdx].teamId,
                        homeTeamScore = null,
                        awayTeamId = teams[0].teamId,
                        awayTeamScore = null,
                    )
                )
            } else {
                fixtures.add(
                    DbFixture(
                        date = fixtureDate.formatToPattern(YYYY_MM_DD),
                        homeTeamId = teams[0].teamId,
                        homeTeamScore = null,
                        awayTeamId = teams[teamIdx].teamId,
                        awayTeamScore = null,
                    )
                )
            }
            for (idx in 1 until halfSize) {
                val firstTeam = (day + idx) % teamsSize
                val secondTeam = (day + teamsSize - idx) % teamsSize
                if (day <= halfSize) {
                    fixtures.add(
                        DbFixture(
                            date = fixtureDate.formatToPattern(YYYY_MM_DD),
                            homeTeamId = teams[firstTeam].teamId,
                            homeTeamScore = null,
                            awayTeamId = teams[secondTeam].teamId,
                            awayTeamScore = null,
                        )
                    )
                    println("${teams[firstTeam]} vs ${teams[secondTeam]}")
                } else {
                    fixtures.add(
                        DbFixture(
                            date = fixtureDate.formatToPattern(YYYY_MM_DD),
                            homeTeamId = teams[secondTeam].teamId,
                            homeTeamScore = null,
                            awayTeamId = teams[firstTeam].teamId,
                            awayTeamScore = null,
                        )
                    )
                }
            }

            fixtureDate = fixtureDate.plusDays(1)
        }

        fixtures.forEach {
            println("${it.date} ${it.homeTeamId} vs ${it.awayTeamId}")
        }

        return fixtures
    }
}

private val mockTeams = listOf(
    DbTeam(name = "Arsenal"),
    DbTeam(name = "United"),
    DbTeam(name = "Chelsea"),
    DbTeam(name = "Leeds"),
)
