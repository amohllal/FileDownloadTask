package com.ahmed.data.di

import com.ahmed.data.datasource.LocalDataSource
import com.ahmed.data.datasource.LocalDataSourceImpl
import com.ahmed.data.repository.FileRepositoryImpl
import com.ahmed.domain.repository.FileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideFileRepository(fileRepositoryImpl: FileRepositoryImpl): FileRepository

    @Binds
    @Singleton
    abstract fun provideLocalDataSource(localDataSourceImpl: LocalDataSourceImpl): LocalDataSource

}