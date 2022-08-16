package com.peter.asteroids.di

import com.peter.asteroids.business.repository.INasaRepository
import com.peter.asteroids.business.repository.NasaRepositoryImpl
import com.peter.asteroids.business.utils.Constants.NASA_API_URL
import com.peter.asteroids.framework.datasource.database.NasaDao
import com.peter.asteroids.framework.datasource.network.NasaAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideNasaAPI(): NasaAPI = Retrofit.Builder()
//        .addConverterFactory(GsonConverterFactory.create())
//        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(OkHttpClient().newBuilder().connectTimeout(2, TimeUnit.MINUTES).build())
        .addConverterFactory(ScalarsConverterFactory.create())
        .baseUrl(NASA_API_URL)
        .build().create(NasaAPI::class.java)

    @Singleton
    @Provides
    fun provideRepository(api: NasaAPI,dao: NasaDao): INasaRepository = NasaRepositoryImpl(api,dao)
}
