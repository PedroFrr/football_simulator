package com.example.footballsimulator.common.di

import android.content.Context
import com.example.footballsimulator.common.data.db.AppDatabase
import com.example.footballsimulator.common.data.db.dao.CompetitionsDao
import com.example.footballsimulator.common.data.db.dao.FixturesDao
import com.example.footballsimulator.common.data.db.dao.PlayersDao
import com.example.footballsimulator.common.data.db.dao.TeamsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModel {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun providePlayersDao(appDatabase: AppDatabase): PlayersDao {
        return appDatabase.playersDao()
    }

    @Provides
    fun provideTeamsDao(appDatabase: AppDatabase): TeamsDao {
        return appDatabase.teamsDao()
    }

    @Provides
    fun provideCompetitionsDao(appDatabase: AppDatabase): CompetitionsDao {
        return appDatabase.competitionsDao()
    }

    @Provides
    fun provideFixturesDao(appDatabase: AppDatabase): FixturesDao {
        return appDatabase.fixturesDao()
    }
}