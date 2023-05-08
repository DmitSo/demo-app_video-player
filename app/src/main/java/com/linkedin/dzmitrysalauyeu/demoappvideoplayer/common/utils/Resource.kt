package com.linkedin.dzmitrysalauyeu.demoappvideoplayer.common.utils

sealed class Resource<out T> {

    class SuccessLocal<T>(val data: T?): Resource<T>()
    class SuccessRemote<T>(val data: T?): Resource<T>()
    class Error<T>(val throwable: Throwable?): Resource<T>()
    class Loading<T>: Resource<T>()
}