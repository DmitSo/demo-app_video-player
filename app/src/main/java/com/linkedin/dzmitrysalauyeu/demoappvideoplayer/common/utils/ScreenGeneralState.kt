package com.linkedin.dzmitrysalauyeu.demoappvideoplayer.common.utils

sealed class ScreenGeneralState {
    object NormalScreenGeneralState: ScreenGeneralState()
    object LoadingScreenGeneralState: ScreenGeneralState()
    class ErrorScreenGeneralState(val t: Throwable): ScreenGeneralState()
}