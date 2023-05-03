package com.linkedin.dzmitrysalauyeu.demoappvideoplayer.di.modules

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
    fun provideVideoPagesInfoBaseUrl() = "https://89.208.230.60/"

    @Provides
    @Singleton
    @VideoPagesInfoApi
    fun getVideoPageOkHttp(baseOkHttpClientBuilder: OkHttpClient.Builder)  =
        baseOkHttpClientBuilder
            .ignoreSecurityCertificates()
            .build()

    @Provides
    @Singleton
    @VideoPagesInfoApi
    fun provideVideoPagesInfoRetrofit(
        baseRetrofitBuilder: Retrofit.Builder,
        @VideoPagesInfoApi videoPageOkHttpClient: OkHttpClient,
        @VideoPagesInfoApi baseUrl: String
    ) = baseRetrofitBuilder
        .client(videoPageOkHttpClient)
        .baseUrl(baseUrl)
        .build()

    @Provides
    @Singleton
    fun provideVideoPagesInfoApiService(
        @VideoPagesInfoApi retrofit: Retrofit
    ) = retrofit.create(VideoPagesInfoApiService::class.java)
}