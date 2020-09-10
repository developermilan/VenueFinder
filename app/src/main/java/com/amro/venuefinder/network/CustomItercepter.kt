package com.amro.venuefinder.network

import okhttp3.Interceptor
import okhttp3.Response

class CustomItercepter : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        return chain.proceed(request)
    }
}