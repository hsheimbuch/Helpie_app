package com.beetzung.helpie.di

import androidx.viewbinding.BuildConfig
import com.beetzung.helpie.data.RecognitionApi
import com.beetzung.helpie.data.RecognitionApiDebug
import com.beetzung.helpie.data.RecognitionApiImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HelpieModule {
    @Provides
    @Singleton
    fun provideRecognitionApi(): RecognitionApi {
        return if (BuildConfig.DEBUG) {
            RecognitionApiDebug()
        } else {
            RecognitionApiImpl()
        }
    }
}