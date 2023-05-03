package com.linkedin.dzmitrysalauyeu.demoappvideoplayer.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.di.ViewModelProviderFactory
import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.di.annotations.ViewModelKey
import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.videoscreen.presentation.pagescontainer.VideoPagesContainerViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap


@Module
@InstallIn(SingletonComponent::class)
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(VideoPagesContainerViewModel::class)
    abstract fun bindsAuthViewModel(viewModel: VideoPagesContainerViewModel?): ViewModel?

    @Binds
    abstract fun bindViewModelFactory(viewModelProviderFactory: ViewModelProviderFactory?): ViewModelProvider.Factory?

}