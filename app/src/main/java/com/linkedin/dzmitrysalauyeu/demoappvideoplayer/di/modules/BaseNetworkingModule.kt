package com.linkedin.dzmitrysalauyeu.demoappvideoplayer.di.modules

import com.google.gson.GsonBuilder
import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BaseNetworkingModule {

    @Provides
    @Singleton
    fun provideBaseGson() = GsonBuilder()
        .create()

    @Provides
    @Singleton
    fun provideBaseOkHttpClientBuilder() = OkHttpClient.Builder()

    @Provides
    @Singleton
    fun provideBaseOkHttpClient(baseOkHttpClientBuilder: OkHttpClient.Builder) =
        baseOkHttpClientBuilder
            .apply {
                if (BuildConfig.DEBUG) {
                    addNetworkInterceptor(HttpLoggingInterceptor()
                        .apply { level = HttpLoggingInterceptor.Level.BODY })
                }
            }
            .build()

    @Provides
    @Singleton
    fun provideBaseRetrofitBuilder(baseOkHttpClient: OkHttpClient) =
        Retrofit.Builder()
            .client(baseOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
}