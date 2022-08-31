package com.example.footballsimulator.fixtures.domain.repository

import com.example.footballsimulator.common.data.db.dao.FixturesDao
import com.example.footballsimulator.common.data.db.dao.TeamsDao
import com.example.footballsimulator.common.util.Constants
import com.example.footballsimulator.common.util.calculateMassFunction
import com.example.footballsimulator.fixtures.domain.Fixture
import com.example.footballsimulator.teams.domain.Team
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.roundToInt
import kotlin.random.Random

@Singleton
class FixturesRepositoryImpl @Inject constructor(
    private val fixturesDao: FixturesDao,
    private val teamsDao: TeamsDao,
) : FixturesRepository {
    override fun getFixtures(): Flow<List<Fixture>> = fixturesDao.fetchFixturesStream().map {
        it.map {
            val homeTeam = teamsDao.getTeamById(it.homeTeamId)
            val awayTeam = teamsDao.getTeamById(it.awayTeamId)
            Fixture(
                date = it.date,
                homeTeamScore = it.homeTeamScore,
                homeTeam = Team(
                    teamId = homeTeam.teamId,
                    name = homeTeam.name
                ),
                awayTeamScore = it.awayTeamScore,
                awayTeam = Team(
                    teamId = awayTeam.teamId,
                    name = awayTeam.name
                ),
            )
        }
    }

    /*
    Calculates the result of each saved fixture
     */
    override suspend fun simulateMatches() {
        val fixtures = fixturesDao.fetchFixtures()
        val updatedFixtures = fixtures.map {
            val homeTeam = teamsDao.getTeamById(it.homeTeamId)
            val awayTeam = teamsDao.getTeamById(it.homeTeamId)

            val averageHomeTeamScoredGoals = homeTeam.numberOfGoalsScoredLastSeason / Constants.NUMBER_OF_GAMES_PER_ROUND
            val homeTeamAttackStrength = averageHomeTeamScoredGoals / Constants.AVERAGE_HOME_SCORED_GOALS_LAST_SEASON

            val averageAwayTeamConcededGoals = awayTeam.numberOfGoalsConcededLastSeason / Constants.NUMBER_OF_GAMES_PER_ROUND
            val awayTeamDefenseStrength = averageAwayTeamConcededGoals / Constants.AVERAGE_AWAY_CONCEDED_GOALS_LAST_SEASON

            val likelyNumberOfHomeTeamGoals = homeTeamAttackStrength * awayTeamDefenseStrength * Constants.AVERAGE_HOME_SCORED_GOALS_LAST_SEASON

            val averageHomeTeamConcededGoals = homeTeam.numberOfGoalsConcededLastSeason / Constants.NUMBER_OF_GAMES_PER_ROUND
            val homeTeamDefenseStrength = averageHomeTeamConcededGoals / Constants.AVERAGE_AWAY_CONCEDED_GOALS_LAST_SEASON

            val averageAwayTeamScoredGoals = awayTeam.numberOfGoalsScoredLastSeason / Constants.NUMBER_OF_GAMES_PER_ROUND
            val awayTeamAttackStrength = averageAwayTeamScoredGoals / Constants.AVERAGE_HOME_SCORED_GOALS_LAST_SEASON

            val likelyNumberOfAwayTeamGoals = awayTeamAttackStrength * homeTeamDefenseStrength * Constants.AVERAGE_HOME_SCORED_GOALS_LAST_SEASON

            val homeTeamGoals = calculateNumberOfGoals(likelyNumberOfHomeTeamGoals)
            val awayTeamGoals = calculateNumberOfGoals(likelyNumberOfAwayTeamGoals)

            it.copy(homeTeamScore = homeTeamGoals, awayTeamScore = awayTeamGoals)
        }

        fixturesDao.deleteAllAndInsertFixtures(updatedFixtures)
    }

    private fun calculateNumberOfGoals(likelyNumberOfGoals: Double): Int {
        val probabilityOfZeroGoals = likelyNumberOfGoals.calculateMassFunction(0)
        val probabilityOfOneGoal = likelyNumberOfGoals.calculateMassFunction(1)
        val probabilityOfTwoGoals = likelyNumberOfGoals.calculateMassFunction(2)
        val probabilityOfThreeGoals = likelyNumberOfGoals.calculateMassFunction(3)
        val probabilityOfFourGoals = likelyNumberOfGoals.calculateMassFunction(4)

        val zeroGoalsRange = 0 until probabilityOfZeroGoals.roundToInt()
        val oneGoalRange = zeroGoalsRange.last until zeroGoalsRange.last + probabilityOfOneGoal.roundToInt()
        val twoGoalsRange = oneGoalRange.last until oneGoalRange.last + probabilityOfTwoGoals.roundToInt()
        val threeGoalsRange = twoGoalsRange.last until twoGoalsRange.last + probabilityOfThreeGoals.roundToInt()
        val fourGoalsRange = threeGoalsRange.last until threeGoalsRange.last + probabilityOfFourGoals.roundToInt()

        return when (Random.nextInt(0, fourGoalsRange.last)) {
            in zeroGoalsRange -> 0
            in oneGoalRange -> 1
            in twoGoalsRange -> 2
            in threeGoalsRange -> 3
            in fourGoalsRange -> 4
            else -> 5
        }
    }
}
