package com.peter.asteroids.framework.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peter.asteroids.business.models.Asteroid
import com.peter.asteroids.business.repository.INasaRepository
import com.peter.asteroids.business.utils.toJson
import com.peter.asteroids.framework.datasource.response.ImageOfDay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: INasaRepository) : ViewModel() {

    private val _data = MutableLiveData<List<Asteroid>>()
    val data: MutableLiveData<List<Asteroid>>
        get() = _data

    private val _selectedAsteroid = MutableLiveData<Asteroid>()
    val selectedAsteroid: MutableLiveData<Asteroid>
        get() = _selectedAsteroid

    private val _imageOfDay = MutableLiveData<ImageOfDay>()
    val imageOfDay: MutableLiveData<ImageOfDay>
        get() = _imageOfDay

    init {
        viewModelScope.launch {
            getAsteroids()
            getImageOfTheDay()
        }
    }

    fun setSelectedAsteroid(asteroid: Asteroid) {
        _selectedAsteroid.value = asteroid
    }

    private suspend fun getImageOfTheDay() {
        repository.getImageOfDay().collect {
            Log.i("Nasa_image_of_day", it.url)
            _imageOfDay.value = it
        }
    }

     suspend fun getAsteroids() {
        repository.getAsteroids().collect {
            Log.i("Nasa_list", it.toJson())
            _data.value = it
        }
    }


     suspend fun getAsteroidsToday() {
        repository.getAsteroidsDay().collect {
            Log.i("Nasa_list", it.toJson())
            _data.value = it
        }
    }

     suspend fun getAsteroidsWeek() {
        repository.getAsteroidsWeek().collect {
            Log.i("Nasa_list", it.toJson())
            _data.value = it
        }
    }


}