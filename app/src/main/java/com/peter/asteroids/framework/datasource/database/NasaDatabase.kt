package com.peter.asteroids.framework.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.peter.asteroids.business.models.Asteroid
import com.peter.asteroids.framework.datasource.response.ImageOfDay

@Database(entities = [Asteroid::class,ImageOfDay::class], version = 3, exportSchema = false)
abstract class NasaDatabase : RoomDatabase() {
    abstract fun dao(): NasaDao
}
