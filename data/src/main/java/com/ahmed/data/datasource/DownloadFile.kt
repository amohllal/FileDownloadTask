package com.ahmed.data.datasource

import android.util.Log
import com.ahmed.data.model.ProgressResponseBody
import com.ahmed.domain.model.File
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.internal.format
import java.io.IOException
import javax.inject.Inject

class DownloadFile @Inject constructor() {

   lateinit var progressListener: ProgressListener

    @Throws(Exception::class)
    operator fun invoke(file: File) {
        try {
            val request = Request.Builder()
                .url(file.url)
                .build()

            val client = OkHttpClient.Builder()
                .addNetworkInterceptor { chain: Interceptor.Chain ->
                    val originalResponse = chain.proceed(chain.request())
                    originalResponse.newBuilder()
                        .body(ProgressResponseBody(originalResponse.body!!, progressListener))
                        .build() }
                .build()

            val response = client.newCall(request).execute()
            if (!response.isSuccessful){
                throw IOException(" Unexpected  code $response")
            }
            response.body?.string()

        } catch (e: Exception) {
            progressListener.failure(-1)
            e.printStackTrace()
        }
    }


}