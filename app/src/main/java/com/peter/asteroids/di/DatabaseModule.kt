package com.peter.asteroids.di

import android.content.Context
import androidx.room.Room
import com.peter.asteroids.framework.datasource.database.NasaDao
import com.peter.asteroids.framework.datasource.database.NasaDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDao(db: NasaDatabase): NasaDao = db.dao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): NasaDatabase =
        Room.databaseBuilder(
            context,
            NasaDatabase::class.java,
            "nasa_db"
        )
            .fallbackToDestructiveMigration()
            .build()

}