package com.example.footballsimulator.common.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.footballsimulator.common.data.db.dao.CompetitionsDao
import com.example.footballsimulator.common.data.db.dao.FixturesDao
import com.example.footballsimulator.common.data.db.dao.PlayersDao
import com.example.footballsimulator.common.data.db.dao.TeamsDao
import com.example.footballsimulator.common.data.db.entities.DbCompetition
import com.example.footballsimulator.common.data.db.entities.DbCompetitionPhase
import com.example.footballsimulator.common.data.db.entities.DbFixture
import com.example.footballsimulator.common.data.db.entities.DbPlayer
import com.example.footballsimulator.common.data.db.entities.DbTeam
import com.example.footballsimulator.common.data.db.workers.FixturesDatabaseWorker
import com.example.footballsimulator.common.data.db.workers.PlayersDatabaseWorker

/**
 * SQLite Database for storing the football simulation data.
 */

private const val DATABASE_NAME = "football_simulation_db"

@Database(entities = [DbPlayer::class, DbTeam::class, DbCompetition::class, DbCompetitionPhase::class, DbFixture::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun playersDao(): PlayersDao
    abstract fun teamsDao(): TeamsDao
    abstract fun competitionsDao(): CompetitionsDao
    abstract fun fixturesDao(): FixturesDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        /*
        Prepopulates database with a first set of fixtures for all rounds
         */
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(
                    object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)

                            //TODO add teams
                            val playersDatabaseWorker = OneTimeWorkRequestBuilder<PlayersDatabaseWorker>().build()
                            val fixturesDatabaseWorker = OneTimeWorkRequestBuilder<FixturesDatabaseWorker>().build()

                            //Chained work - executes the workers in the defined order below
                            WorkManager.getInstance(context)
                                .beginWith(playersDatabaseWorker)
                                .then(fixturesDatabaseWorker)
                                .enqueue()
                        }
                    }
                )
                .build()
        }
    }
}
