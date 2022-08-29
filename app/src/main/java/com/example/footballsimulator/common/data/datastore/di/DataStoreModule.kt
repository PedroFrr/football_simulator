package com.example.footballsimulator.common.data.datastore.di

import android.content.Context
import com.example.footballsimulator.common.data.datastore.ApplicationPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    fun providePreferencesDataStore(
        @ApplicationContext context: Context,
    ): ApplicationPreferences = ApplicationPreferences(context)
}
