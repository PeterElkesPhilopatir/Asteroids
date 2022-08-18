package com.peter.asteroids.framework.datasource.database

import android.media.Image
import androidx.room.*
import com.peter.asteroids.business.models.Asteroid
import com.peter.asteroids.framework.datasource.response.ImageOfDay
import kotlinx.coroutines.flow.Flow

@Dao
interface NasaDao {

    @Query("SELECT * FROM asteroid_tbl ORDER BY closeApproachDate DESC")
    fun getAsteroids(): List<Asteroid>

    @Query("SELECT * FROM asteroid_tbl WHERE closeApproachDate = :startDate ORDER BY closeApproachDate DESC")
    fun getAsteroidsByDay(startDate: String): List<Asteroid>

    @Query("SELECT * FROM asteroid_tbl WHERE closeApproachDate BETWEEN :startDate AND :endDate ORDER BY closeApproachDate DESC")
    fun getAsteroidsByPeriod(startDate: String, endDate: String): List<Asteroid>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsteroid(asteroid: Asteroid)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(asteroid: Asteroid)

    @Query(value = "DELETE from asteroid_tbl")
    suspend fun deleteAsteroids()

    @Delete
    suspend fun delete(asteroid: Asteroid)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImageOfDay(imageOfDay: ImageOfDay)

    @Query(value = "DELETE from image_tbl")
    suspend fun deleteImageOfDay()

    @Query(value = "select * from image_tbl")
    fun getImages(): List<ImageOfDay>



}