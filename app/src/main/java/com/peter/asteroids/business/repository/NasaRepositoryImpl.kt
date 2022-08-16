package com.peter.asteroids.business.repository

import android.util.Log
import com.peter.asteroids.business.models.Asteroid
import com.peter.asteroids.business.utils.Constants
import com.peter.asteroids.framework.datasource.database.NasaDao
import com.peter.asteroids.framework.datasource.network.NasaAPI
import com.peter.asteroids.framework.datasource.network.getParseImageOfTheDay
import com.peter.asteroids.framework.datasource.network.parseNasaResponse
import com.peter.asteroids.framework.datasource.response.ImageOfDay
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.awaitResponse
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class NasaRepositoryImpl @Inject constructor(private val api: NasaAPI, private val dao: NasaDao) :
    INasaRepository {
    override fun getAsteroids(): Flow<List<Asteroid>> =
        flow {
            try {
                val response = api.getDataAsync(
                    SimpleDateFormat(
                        Constants.DATE_FORMAT,
                        Locale.getDefault()
                    ).format(Calendar.getInstance().time)
                ).awaitResponse()
                if (response.isSuccessful) {
                    Log.i("Nasa", response.body().toString())
                    val list = parseNasaResponse(response.body().toString())
                    list.forEach { dao.insertAsteroid(it) }
                    emit(list)
                } else {
                    emit(dao.getAsteroids())
                }
            } catch (e: Exception) {
                emit(dao.getAsteroids())
            }
        }.flowOn(IO)

    override fun getImageOfDay(): Flow<ImageOfDay> = flow {
        try {
            val response = api.getImageOfDayAsync().awaitResponse()

            if (response.isSuccessful) {
                val result = getParseImageOfTheDay(response.body().toString())
                dao.deleteImageOfDay()
                dao.insertImageOfDay(result)
                emit(result)
            } else emit(dao.getImages().first())
        } catch (e: Exception) {
            val result = dao.getImages()
            if (result.isNotEmpty()) {
                emit(dao.getImages().first())
            } else {
                emit(ImageOfDay("", "", "", "", "", "", ""))
            }
        }
    }.flowOn(IO)
}