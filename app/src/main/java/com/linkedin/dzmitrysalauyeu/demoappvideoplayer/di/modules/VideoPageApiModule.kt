package com.linkedin.dzmitrysalauyeu.demoappvideoplayer.di.modules

import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.di.annotations.VideoPageApi
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
    fun getVideoPageRetrofit(baseBuilder: Retrofit.Builder): Retrofit = baseBuilder.build()
}