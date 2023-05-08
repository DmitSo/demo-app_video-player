package com.linkedin.dzmitrysalauyeu.demoappvideoplayer.di.modules

import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.BuildConfig
import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.di.annotations.VideoPagesInfoApi
import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.common.utils.ignoreSecurityCertificates
import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.videoscreen.data.apiservice.VideoPagesInfoApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class VideoPagesInfoApiModule {

    @Provides
    @Singleton
    @VideoPagesInfoApi
    fun provideVideoPagesInfoBaseUrl() = BuildConfig.BASE_URL_VIDEO_PAGES_INFO

    @Provides
    @Singleton
    @VideoPagesInfoApi
    fun getVideoPageOkHttp(baseOkHttpClientBuilder: OkHttpClient.Builder)  =
        baseOkHttpClientBuilder
            .ignoreSecurityCertificates()   // was a requirement according to the test task
            .build()

    @Provides
    @Singleton
    @VideoPagesInfoApi
    fun provideVideoPagesInfoRetrofit(
        baseRetrofitBuilder: Retrofit.Builder,
        @VideoPagesInfoApi videoPageOkHttpClient: OkHttpClient,
        @VideoPagesInfoApi baseUrl: String
    ): Retrofit = baseRetrofitBuilder
        .client(videoPageOkHttpClient)
        .baseUrl(baseUrl)
        .build()

    @Provides
    @Singleton
    fun provideVideoPagesInfoApiService(
        @VideoPagesInfoApi retrofit: Retrofit
    ): VideoPagesInfoApiService = retrofit.create(VideoPagesInfoApiService::class.java)
}