package com.example.footballsimulator.common.di

import com.example.footballsimulator.fixtures.domain.repository.FixturesRepository
import com.example.footballsimulator.fixtures.domain.repository.FixturesRepositoryImpl
import com.example.footballsimulator.players.domain.PlayersRepository
import com.example.footballsimulator.players.domain.PlayersRepositoryImpl
import com.example.footballsimulator.teams.domain.TeamsRepository
import com.example.footballsimulator.teams.domain.TeamsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesFixturesRepository(
        impl: FixturesRepositoryImpl
    ): FixturesRepository

    @Binds
    abstract fun providesTeamsRepository(
        impl: TeamsRepositoryImpl
    ): TeamsRepository

    @Binds
    abstract fun providesPlayersRepository(
        impl: PlayersRepositoryImpl
    ): PlayersRepository

}