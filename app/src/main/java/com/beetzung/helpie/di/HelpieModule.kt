package com.beetzung.helpie.di

import android.content.Context
import android.location.Location
import androidx.viewbinding.BuildConfig
import com.beetzung.helpie.data.Database
import com.beetzung.helpie.data.LocationService
import com.beetzung.helpie.data.RecognitionApi
import com.beetzung.helpie.data.model.implementation.DatabaseDebug
import com.beetzung.helpie.data.model.implementation.LocationServiceDebug
import com.beetzung.helpie.data.model.implementation.RecognitionApiDebug
import com.beetzung.helpie.data.model.implementation.RecognitionApiImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HelpieModule {
    @Provides
    @Singleton
    fun provideRecognitionApi(): RecognitionApi {
        return RecognitionApiImpl()
//        return if (BuildConfig.DEBUG) {
//            RecognitionApiDebug()
//        } else {
//            RecognitionApiImpl()
//        } TODO add real api
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): Database {
        return DatabaseDebug(context)
        //TODO add real database
    }

    @Provides
    @Singleton
    fun provideLocationService(@ApplicationContext context: Context): LocationService {
        return LocationServiceDebug()
        //TODO add real location service
    }
}