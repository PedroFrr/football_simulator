package com.example.footballsimulator.common.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.footballsimulator.common.data.db.entities.DbFixture
import com.example.footballsimulator.common.data.db.entities.DbTeamStanding
import kotlinx.coroutines.flow.Flow

@Dao
interface FixturesDao {

    @Query("SELECT * FROM fixtures_table")
    fun fetchFixturesStream(): Flow<List<DbFixture>>

    @Query("SELECT * FROM fixtures_table")
    suspend fun fetchFixtures(): List<DbFixture>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFixtures(fixtures: List<DbFixture>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(fixtures: List<DbFixture>)

    @Query("DELETE FROM fixtures_table")
    fun deleteAll()

    /**
     * First deletes all existing fixtures before inserting the new ones. Unfortunately this doesn't allow historic data
     * Since it's easier to work with for the assignment
     */
    @Transaction
    suspend fun deleteAllAndInsertFixtures(fixtures: List<DbFixture>) {
        deleteAll()
        insertAll(fixtures)
    }

    @Query(
        "SELECT" +
            "      homeTeam.name as Club," +
            "      (h_win + a_win + h_draw + a_draw + h_loss + a_loss) as MP," +
            "      (h_win + a_win) as W," +
            "      (h_draw + a_draw) as D," +
            "      (h_loss + a_loss) as L," +
            "      (h_goals_for+a_goals_for) as GF," +
            "      (h_goals_agst+a_goals_agst) as GA," +
            "      (h_goals_for+a_goals_for-h_goals_agst-a_goals_agst) as GD," +
            "      ((h_win + a_win)*3+(h_draw + a_draw)) as Pts" +
            "      FROM" +
            "(SELECT name," +
            "       SUM(CASE WHEN homeTeamScore > awayTeamScore THEN 1 ELSE 0 END) as h_win," +
            "       SUM(CASE WHEN homeTeamScore = awayTeamScore THEN 1 ELSE 0 END) as h_draw," +
            "       SUM(CASE WHEN homeTeamScore < awayTeamScore THEN 1 ELSE 0 END) as h_loss," +
            "       SUM(homeTeamScore) as h_goals_for," +
            "       SUM(awayTeamScore) as h_goals_agst" +
            "       FROM fixtures_table" +
            "       JOIN teams_table as homeTeam ON homeTeam.teamId == fixtures_table.homeTeamId" +
            "              GROUP BY homeTeamId" +
            "              ORDER BY homeTeamId) as homeTeam" +
            " JOIN" +
            " (SELECT name," +
            "          SUM(CASE WHEN homeTeamScore > awayTeamScore THEN 1 ELSE 0 END) as a_win," +
            "          SUM(CASE WHEN homeTeamScore = awayTeamScore THEN 1 ELSE 0 END) as a_draw," +
            "          SUM(CASE WHEN homeTeamScore < awayTeamScore THEN 1 ELSE 0 END) as a_loss," +
            "          SUM(homeTeamScore) as a_goals_for," +
            "          SUM(awayTeamScore) as a_goals_agst" +
            "          FROM fixtures_table" +
            "          JOIN teams_table as awayteam ON awayteam.teamId == fixtures_table.awayTeamId" +
            "                 GROUP BY awayTeamId" +
            "                 ORDER BY awayTeamId) as awayTeam" +
            "                 " +
            "ON   (homeTeam.name == awayTeam.name) ORDER BY pts desc"
    )
    suspend fun fetchTeamStandings(): List<DbTeamStanding>
}
