package com.peter.asteroids.business.repository

import com.peter.asteroids.business.models.Asteroid
import com.peter.asteroids.framework.datasource.response.ImageOfDay
import kotlinx.coroutines.flow.Flow

interface INasaRepository {
    fun getAsteroids(): Flow<List<Asteroid>>

    fun getImageOfDay() : Flow<ImageOfDay>
}