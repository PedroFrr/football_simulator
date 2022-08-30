package com.example.footballsimulator.common.data.db.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.footballsimulator.common.data.db.dao.FixturesDao
import com.example.footballsimulator.common.data.db.dao.TeamsDao
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
    private val fixturesDao: FixturesDao, //TODO replace with repository
    private val teamsDao: TeamsDao //TODO replace with repository
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        //TODO generate the first batch of fixtures


        val teams = teamsDao.fetchTeams()
        val randomFourTeams = teams.asSequence()
            .shuffled()
            .take(4)
            .toList()

        val fixtures = generateFixtures(randomFourTeams)

        fixturesDao.insertAll(fixtures)

        return Result.success()
    }

    /**
     * Berger table algorithm to generate fixtures for all teams home and away
     */
    private fun generateFixtures(teamsList: List<DbTeam>): List<DbFixture> {
        var tournamentStartDate = LocalDate.of(2022, Month.JULY, 30)
        val numTeams = teamsList.size
        val numDays: Int = (numTeams - 1) // Days needed to complete tournament
        val halfSize: Int = numTeams / 2
        val teams = mutableListOf<DbTeam>()
        val fixtures = mutableListOf<DbFixture>()
        teams.addAll(teamsList) // Add teams to List and remove the first team
        teams.removeAt(0)
        val teamsSize: Int = teams.size
        for (day in 0 until numDays) {
            tournamentStartDate = tournamentStartDate.plusDays(1)
            val teamIdx = day % teamsSize
            fixtures.add(
                DbFixture(
                    date = tournamentStartDate.formatToPattern(YYYY_MM_DD),
                    homeTeamId = teams[teamIdx].teamId,
                    homeTeamScore = null,
                    awayTeamId = teamsList[0].teamId,
                    awayTeamScore = null,
                )
            )
            for (idx in 1 until halfSize) {
                val firstTeam = (day + idx) % teamsSize
                val secondTeam = (day + teamsSize - idx) % teamsSize
                fixtures.add(
                    DbFixture(
                        date = tournamentStartDate.formatToPattern(YYYY_MM_DD),
                        homeTeamId = teams[firstTeam].teamId,
                        homeTeamScore = null,
                        awayTeamId = teams[secondTeam].teamId,
                        awayTeamScore = null,
                    )
                )
            }
        }

        var secondRoundStartDate = tournamentStartDate.plusWeeks(2) // second round starts two weeks after

        //Now for next round, Rotate team Array
        val secondRound = fixtures.mapIndexed { index, dbFixture ->
            if (index % 2 == 0) secondRoundStartDate = secondRoundStartDate.plusDays(1)
            DbFixture(
                date = tournamentStartDate.formatToPattern(YYYY_MM_DD),
                homeTeamId = dbFixture.awayTeamId,
                homeTeamScore = null,
                awayTeamId = dbFixture.homeTeamId,
                awayTeamScore = null,
            )
        }

        fixtures.addAll(secondRound)

        return fixtures
    }
}
