package com.example.madlevel6task2.api

import com.example.madlevel6task2.BuildConfig
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * An interceptor to add the 'api_key' parameter to every request send to the 'Movies Database API'
 */
internal class KeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val originalHttpUrl: HttpUrl = original.url

        // Add the API key to the request
        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("api_key", BuildConfig.MOVIE_DATABASE_KEY)
            .build()

        // Request customization: add request headers
        val requestBuilder: Request.Builder = original.newBuilder()
            .url(url)

        val request: Request = requestBuilder.build()
        return chain.proceed(request)
    }
}