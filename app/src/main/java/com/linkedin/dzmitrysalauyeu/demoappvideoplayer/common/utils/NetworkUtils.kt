package com.linkedin.dzmitrysalauyeu.demoappvideoplayer.common.utils

import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import java.lang.Exception
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

fun OkHttpClient.Builder.ignoreSecurityCertificates() : OkHttpClient.Builder {
    val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
        @Throws(CertificateException::class)
        override fun checkClientTrusted(
            chain: Array<X509Certificate>,
            authType: String
        ) {
        }

        @Throws(CertificateException::class)
        override fun checkServerTrusted(
            chain: Array<X509Certificate>,
            authType: String
        ) {
        }

        override fun getAcceptedIssuers(): Array<X509Certificate> =
            arrayOf()
    })
    // Install the all-trusting trust manager
    val sslContext = SSLContext.getInstance("SSL")
    sslContext.init(null, trustAllCerts, SecureRandom())
    // Create an ssl socket factory with our all-trusting manager
    val sslSocketFactory = sslContext.socketFactory

    sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
    hostnameVerifier(hostnameVerifier = HostnameVerifier { _, _ -> true })

    return this
}

fun mapNetworkError(errorBody: ResponseBody?): Throwable {
    return Exception(errorBody?.string())
}