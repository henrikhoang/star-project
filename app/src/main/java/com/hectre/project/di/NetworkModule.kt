package com.hectre.project.di

import android.app.Application
import android.content.Context
import com.hectre.project.network.remote.MockInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    fun provideMockInterceptor(): MockInterceptor = MockInterceptor()

    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

    @Provides
    fun provideOkHttpClient(mockInterceptor: MockInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(mockInterceptor)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .callTimeout(15, TimeUnit.SECONDS)
            .build()
}