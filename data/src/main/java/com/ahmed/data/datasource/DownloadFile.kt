package com.ahmed.data.datasource

import com.ahmed.data.model.ProgressResponseBody
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import javax.inject.Inject

class DownloadFile @Inject constructor() {

   lateinit var progressListener: ProgressListener

    @Throws(Exception::class)
    operator fun invoke(fileUrl: String) {
        try {
            val request = Request.Builder()
                .url(fileUrl)
                .build()

            val client = OkHttpClient.Builder()
                .addNetworkInterceptor { chain: Interceptor.Chain ->
                    val originalResponse = chain.proceed(chain.request())
                    originalResponse.newBuilder()
                        .body(originalResponse.body?.let { ProgressResponseBody(
                                it,
                                progressListener
                            )
                        })
                        .build() }
                .build()

            val response = client.newCall(request).execute()
            if (!response.isSuccessful) throw IOException(" Unexpected  code $response")
            print(response.body.toString())

        } catch (e: Exception) {
             e.printStackTrace();
        }
    }

    interface ProgressListener {
         fun update(bytesRead: Long, contentLength: Long, done: Boolean)
    }

}