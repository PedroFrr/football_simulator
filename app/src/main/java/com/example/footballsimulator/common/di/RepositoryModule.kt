package com.example.footballsimulator.common.di

import com.example.footballsimulator.fixtures.domain.repository.FixturesRepository
import com.example.footballsimulator.fixtures.domain.repository.FixturesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesRepository(
        impl: FixturesRepositoryImpl
    ): FixturesRepository
}