package com.business.fitrack.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.business.fitrack.data.repository.WorkoutRepositoryImpl
import com.business.fitrack.domain.WorkoutRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class WorkoutRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWorkoutRepository (workoutRepositoryImpl: WorkoutRepositoryImpl): WorkoutRepository
}