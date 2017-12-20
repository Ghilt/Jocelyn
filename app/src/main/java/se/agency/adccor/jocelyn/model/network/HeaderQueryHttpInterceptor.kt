package se.agency.adccor.jocelyn.model.network

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by adamn on 12/16/2017.
 */
class HeaderQueryHttpInterceptor : okhttp3.Interceptor {

    private val queryParams = ArrayList<Pair<String, String>>()
    private val headers = ArrayList<Pair<String, String>>()

    fun addQuaryParam(name: String, value: String) {
        queryParams.add(Pair(name, value))
    }

    fun addHeader(name: String, value: String) {
        headers.add(Pair(name, value))
    }

    override fun intercept(chain: Interceptor.Chain): Response {

        val original = chain.request()
        val urlBuilder = original.url().newBuilder()
        val requestBuilder = original.newBuilder()
        queryParams.fold(urlBuilder, operation = { builder, kv -> builder.addQueryParameter(kv.first, kv.second) })
        headers.fold(requestBuilder, operation = { builder, kv -> builder.header(kv.first, kv.second) })
        requestBuilder.url(urlBuilder.build())
        return chain.proceed(requestBuilder.build())
    }
}


