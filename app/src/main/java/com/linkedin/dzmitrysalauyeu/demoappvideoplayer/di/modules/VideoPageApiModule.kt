package com.linkedin.dzmitrysalauyeu.demoappvideoplayer.di.modules

import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.di.annotations.VideoPageApi
import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.videoscreen.data.apiservice.VideoPageApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class VideoPageApiModule {

    @Provides
    @Singleton
    @VideoPageApi
    fun getVideoPageRetrofit(baseBuilder: Retrofit.Builder) = baseBuilder.build()

    @Provides
    @Singleton
    fun getVideoPageApi(@VideoPageApi retrofit: Retrofit) = retrofit.create(VideoPageApiService::class.java)
}