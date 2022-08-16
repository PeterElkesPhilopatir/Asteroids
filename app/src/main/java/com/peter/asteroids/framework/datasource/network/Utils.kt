package com.peter.asteroids.framework.datasource.network

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.peter.asteroids.business.models.Asteroid
import com.peter.asteroids.business.utils.toJson
import com.peter.asteroids.framework.datasource.response.ImageOfDay
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@RequiresApi(Build.VERSION_CODES.N)
fun parseNasaResponse(jsonResult: String): ArrayList<Asteroid> {

    val jsonObject = JSONObject(jsonResult)
    val nearEarthObjectsJson = jsonObject.getJSONObject("near_earth_objects")

    val asteroidList = ArrayList<Asteroid>()

    val week = getNextWeek()
    Log.i("Nasa_Week", week.toJson())
    for (date in week) {
        val dateAsteroidJsonArray = nearEarthObjectsJson.getJSONArray(date)

        repeat(dateAsteroidJsonArray.length()) {
            val asteroidJson = dateAsteroidJsonArray.getJSONObject(it)
            val id = asteroidJson.getLong("id")
            val codename = asteroidJson.getString("name")
            val absoluteMagnitude = asteroidJson.getDouble("absolute_magnitude_h")
            val estimatedDiameter = asteroidJson.getJSONObject("estimated_diameter")
                .getJSONObject("kilometers").getDouble("estimated_diameter_max")

            val closeApproachData = asteroidJson
                .getJSONArray("close_approach_data").getJSONObject(0)
            val relativeVelocity = closeApproachData.getJSONObject("relative_velocity")
                .getDouble("kilometers_per_second")
            val distanceFromEarth = closeApproachData.getJSONObject("miss_distance")
                .getDouble("astronomical")
            val isPotentiallyHazardous = asteroidJson
                .getBoolean("is_potentially_hazardous_asteroid")

            val asteroid = Asteroid(
                id, codename, date, absoluteMagnitude,
                estimatedDiameter, relativeVelocity, distanceFromEarth, isPotentiallyHazardous
            )
            asteroidList.add(asteroid)
        }
    }

    return asteroidList
}

@RequiresApi(Build.VERSION_CODES.N)
private fun getNextWeek(): ArrayList<String> {
    val list = ArrayList<String>()

    val calendar = Calendar.getInstance()
    for (i in 0..6) {
        val currentTime = calendar.time
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        list.add(dateFormat.format(currentTime))
        calendar.add(Calendar.DAY_OF_YEAR, 1)
    }

    return list
}

@RequiresApi(Build.VERSION_CODES.N)
fun getParseImageOfTheDay(jsonResult: String): ImageOfDay =

    ImageOfDay(
        date = JSONObject(jsonResult).getString("date"),
        explanation = JSONObject(jsonResult).getString("explanation"),
        hdurl = JSONObject(jsonResult).getString("hdurl"),
        media_type = JSONObject(jsonResult).getString("media_type"),
        service_version = JSONObject(jsonResult).getString("service_version"),
        title = JSONObject(jsonResult).getString("title"),
        url = JSONObject(jsonResult).getString("url")
    )

